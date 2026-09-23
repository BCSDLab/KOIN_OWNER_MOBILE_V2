package `in`.koreatech.business.feature.signup

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.core.term.TermsContentProvider
import `in`.koreatech.business.domain.error.signup.PhoneNumberAlreadyExistsException
import `in`.koreatech.business.domain.model.store.AttachStore
import `in`.koreatech.business.domain.model.upload.PreSignedUrlDomain
import `in`.koreatech.business.domain.usecase.presignedurl.UploadImageUseCase
import `in`.koreatech.business.domain.usecase.signup.CheckCompanyNumberUseCase
import `in`.koreatech.business.domain.usecase.signup.RegisterOwnerUseCase
import `in`.koreatech.business.domain.usecase.signup.SendSignupSmsCodeUseCase
import `in`.koreatech.business.domain.usecase.signup.VerifySignupSmsCodeUseCase
import `in`.koreatech.business.domain.usecase.store.SearchStoresUseCase
import `in`.koreatech.business.domain.util.formatBusinessNumber
import `in`.koreatech.business.domain.util.isValidPhoneNumber
import `in`.koreatech.business.feature.signup.mapper.toOwnerRegistration
import `in`.koreatech.business.feature.signup.mapper.toStoreUrl
import `in`.koreatech.business.feature.signup.verification.PhoneNumberVerificationState
import `in`.koreatech.business.feature.signup.verification.VerificationCodeState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.OrbitContainerHost
import org.orbitmvi.orbit.blockingIntent
import org.orbitmvi.orbit.viewmodel.orbitContainer

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class, binding<ViewModel>())
class SignupViewModel(
    private val sendSignupSmsCodeUseCase: SendSignupSmsCodeUseCase,
    private val verifySignupSmsCodeUseCase: VerifySignupSmsCodeUseCase,
    private val checkCompanyNumberUseCase: CheckCompanyNumberUseCase,
    private val registerOwnerUseCase: RegisterOwnerUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val searchStoresUseCase: SearchStoresUseCase,
    private val termsContentProvider: TermsContentProvider
) : ViewModel(), OrbitContainerHost<SignupState, SignupState, SignupSideEffect> {
    override val container = orbitContainer<SignupState, SignupSideEffect>(SignupState(), onCreate = { loadTerms() })

    private var storeSearchJob: Job? = null

    private suspend fun loadTerms() = subIntent {
        val terms = termsContentProvider.getTerms()
        reduce {
            state.copy(
                privacyTerm = terms.privacy,
                koinTerm = terms.service,
                marketingTerm = terms.marketing
            )
        }
    }

    fun setAllTermsAgreed(isChecked: Boolean) = blockingIntent {
        reduce {
            state.copy(
                agreedToService = isChecked,
                agreedToPrivacy = isChecked,
                agreedToMarketing = isChecked
            )
        }
    }

    fun setServiceTermsAgreed(isChecked: Boolean) = blockingIntent {
        reduce { state.copy(agreedToService = isChecked) }
    }

    fun setPrivacyTermsAgreed(isChecked: Boolean) = blockingIntent {
        reduce { state.copy(agreedToPrivacy = isChecked) }
    }

    fun setMarketingTermsAgreed(isChecked: Boolean) = blockingIntent {
        reduce { state.copy(agreedToMarketing = isChecked) }
    }

    fun updatePhoneNumber(value: String) = blockingIntent {
        reduce {
            state.copy(
                phoneNumber = value.filter(Char::isDigit).take(11),
                verificationCode = "",
                isVerificationCodeSent = false,
                phoneNumberVerificationState = PhoneNumberVerificationState.None,
                verificationCodeState = VerificationCodeState.None,
                verificationToken = null,
                error = null
            )
        }
    }

    fun updateVerificationCode(value: String) = blockingIntent {
        reduce {
            state.copy(
                verificationCode = value.filter(Char::isDigit).take(6),
                verificationCodeState = VerificationCodeState.None,
                verificationToken = null,
                error = null
            )
        }
    }

    fun updateName(value: String) = blockingIntent { reduce { state.copy(name = value, error = null) } }

    fun updatePassword(value: String) = blockingIntent { reduce { state.copy(password = value, error = null) } }

    fun updatePasswordConfirmation(value: String) = blockingIntent {
        reduce { state.copy(passwordConfirmation = value, error = null) }
    }

    fun updateBusinessNumber(value: String) = blockingIntent {
        reduce {
            state.copy(
                businessNumber = value.filter(Char::isDigit).take(10),
                error = null
            )
        }
    }

    fun updateStoreName(value: String) = blockingIntent {
        reduce {
            state.copy(
                storeName = value,
                selectedStoreId = null,
                error = null
            )
        }
    }

    fun updateStorePhoneNumber(value: String) = blockingIntent {
        reduce {
            state.copy(
                storePhoneNumber = value.filter(Char::isDigit).take(11),
                selectedStoreId = null,
                error = null
            )
        }
    }

    fun updateStoreSearchQuery(value: String) {
        storeSearchJob?.cancel()
        storeSearchJob = intent {
            reduce { state.copy(storeSearchQuery = value, selectedStoreId = null, error = null) }
            delay(STORE_SEARCH_DEBOUNCE_MILLIS)
            searchStores(value)
        }
    }

    fun loadStores() {
        storeSearchJob?.cancel()
        storeSearchJob = intent { searchStores(state.storeSearchQuery) }
    }

    private suspend fun searchStores(query: String) = subIntent {
        searchStoresUseCase(query.trim())
            .onStart { reduce { state.copy(isSearchingStores = true) } }
            .onEach { result ->
                result
                    .onSuccess { stores ->
                        reduce {
                            state.copy(
                                storeSearchResults = stores.toImmutableList(),
                                isSearchingStores = false
                            )
                        }
                    }.onFailure {
                        reduce {
                            state.copy(
                                storeSearchResults = persistentListOf(),
                                isSearchingStores = false,
                                error = SignupError.StoreSearch
                            )
                        }
                    }
            }.collect()
    }

    fun selectStore(storeId: Int) = blockingIntent {
        reduce { state.copy(selectedStoreId = storeId) }
    }

    fun applySelectedStore() = intent {
        val store = state.storeSearchResults.firstOrNull { it.id == state.selectedStoreId } ?: return@intent
        reduce {
            state.copy(
                storeName = store.name,
                storePhoneNumber = store.phone.filter(Char::isDigit).take(11),
                error = null
            )
        }
        postSideEffect(SignupSideEffect.NavigateBackFromStoreSearch)
    }

    fun uploadFile(
        fileName: String,
        mediaType: String,
        bytes: ByteArray
    ) = intent {
        if (state.fileInfo.size >= 5 || state.isUploading) return@intent
        val verificationToken = state.verificationToken ?: run {
            reduce { state.copy(error = SignupError.PhoneVerificationRequired) }
            return@intent
        }
        reduce { state.copy(isUploading = true, error = null) }
        uploadImageUseCase(
            domain = PreSignedUrlDomain.OWNERS,
            contentLength = bytes.size.toLong(),
            contentType = mediaType,
            fileName = fileName,
            bytes = bytes,
            authorizationToken = verificationToken
        ).onSuccess { resultUrl ->
            reduce {
                state.copy(
                    isUploading = false,
                    selectedImages = (state.selectedImages + AttachStore(resultUrl, fileName)).toImmutableList(),
                    fileInfo = (state.fileInfo + resultUrl.toStoreUrl(fileName, mediaType, bytes.size.toLong())).toImmutableList()
                )
            }
        }.onFailure { error ->
            reduce {
                state.copy(
                    isUploading = false,
                    error = error.message?.let(SignupError::Dynamic) ?: SignupError.FileUpload
                )
            }
        }
    }

    fun removeFile(index: Int) = blockingIntent {
        reduce {
            state.copy(
                selectedImages = state.selectedImages.filterIndexed { fileIndex, _ -> fileIndex != index }.toImmutableList(),
                fileInfo = state.fileInfo.filterIndexed { fileIndex, _ -> fileIndex != index }.toImmutableList()
            )
        }
    }

    fun onFileSelectionFailed() = blockingIntent {
        reduce { state.copy(error = SignupError.FileUpload) }
    }

    fun navigateToAccount() = intent {
        postSideEffect(SignupSideEffect.NavigateToAccount)
    }

    fun navigateToPassword() = intent {
        postSideEffect(SignupSideEffect.NavigateToPassword)
    }

    fun navigateToBusiness() = intent {
        postSideEffect(SignupSideEffect.NavigateToBusiness)
    }

    fun navigateToAttachments() = intent {
        postSideEffect(SignupSideEffect.NavigateToAttachments)
    }

    fun completeSignup() = intent {
        postSideEffect(SignupSideEffect.CompleteSignup)
    }

    fun sendVerificationCode() = intent {
        if (state.isLoading) return@intent
        if (!state.phoneNumber.isValidPhoneNumber) {
            reduce { state.copy(phoneNumberVerificationState = PhoneNumberVerificationState.WrongFormat) }
            return@intent
        }
        reduce { state.copy(isLoading = true, error = null) }
        sendSignupSmsCodeUseCase(state.phoneNumber)
            .onSuccess {
                reduce {
                    state.copy(
                        isLoading = false,
                        isVerificationCodeSent = true,
                        phoneNumberVerificationState = PhoneNumberVerificationState.Sent,
                        verificationCodeState = VerificationCodeState.None
                    )
                }
            }.onFailure { error ->
                reduce {
                    state.copy(
                        isLoading = false,
                        phoneNumberVerificationState = if (error is PhoneNumberAlreadyExistsException) {
                            PhoneNumberVerificationState.AlreadySignedUp
                        } else {
                            PhoneNumberVerificationState.Failed(error.message)
                        }
                    )
                }
            }
    }

    fun verifyCode() = intent {
        if (!state.isVerificationCodeSent || state.verificationCode.length != 6 || state.isLoading) return@intent
        reduce { state.copy(isLoading = true, error = null) }
        verifySignupSmsCodeUseCase(state.phoneNumber, state.verificationCode)
            .onSuccess { token ->
                reduce {
                    state.copy(
                        isLoading = false,
                        verificationCodeState = VerificationCodeState.Valid,
                        verificationToken = token
                    )
                }
            }.onFailure {
                reduce {
                    state.copy(
                        isLoading = false,
                        verificationCodeState = VerificationCodeState.NotValid
                    )
                }
            }
    }

    fun checkBusinessNumber() = intent {
        if (state.businessNumber.length != 10 || state.isLoading) return@intent
        reduce { state.copy(isLoading = true, error = null) }
        checkCompanyNumberUseCase(state.businessNumber.formatBusinessNumber())
            .onSuccess {
                reduce { state.copy(isLoading = false) }
                postSideEffect(SignupSideEffect.NavigateToStore)
            }.onFailure { error ->
                reduce {
                    state.copy(
                        isLoading = false,
                        error = error.message?.let(SignupError::Dynamic)
                            ?: SignupError.BusinessNumberCheck
                    )
                }
            }
    }

    fun register() = intent {
        val token = state.verificationToken ?: return@intent
        if (state.isLoading) return@intent
        reduce { state.copy(isLoading = true, error = null) }
        registerOwnerUseCase(
            registration = state.toOwnerRegistration(),
            verificationToken = token
        ).onSuccess {
            reduce { state.copy(isLoading = false) }
            postSideEffect(SignupSideEffect.NavigateToComplete)
        }.onFailure { error ->
            reduce {
                state.copy(
                    isLoading = false,
                    error = error.message?.let(SignupError::Dynamic) ?: SignupError.Submit
                )
            }
        }
    }

    private companion object {
        const val STORE_SEARCH_DEBOUNCE_MILLIS = 300L
    }
}
