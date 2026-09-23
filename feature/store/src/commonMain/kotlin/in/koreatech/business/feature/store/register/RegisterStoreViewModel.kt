package `in`.koreatech.business.feature.store.register

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import `in`.koreatech.business.domain.model.store.OwnerShop
import `in`.koreatech.business.domain.model.upload.PreSignedUrlDomain
import `in`.koreatech.business.domain.usecase.address.SearchAddressUseCase
import `in`.koreatech.business.domain.usecase.presignedurl.UploadImageUseCase
import `in`.koreatech.business.domain.usecase.store.GetOwnerShopUseCase
import `in`.koreatech.business.domain.usecase.store.GetShopCategoriesUseCase
import `in`.koreatech.business.domain.usecase.store.SaveOwnerShopUseCase
import `in`.koreatech.business.feature.store.register.mapper.toOwnerShopForm
import `in`.koreatech.business.feature.store.register.mapper.toRegisterStoreState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.orbitmvi.orbit.OrbitContainerHost
import org.orbitmvi.orbit.blockingIntent
import org.orbitmvi.orbit.viewmodel.orbitContainer

class RegisterStoreViewModel internal constructor(
    savedStateHandle: SavedStateHandle,
    private val getShopCategoriesUseCase: GetShopCategoriesUseCase,
    private val getOwnerShopUseCase: GetOwnerShopUseCase,
    private val saveOwnerShopUseCase: SaveOwnerShopUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val searchAddressUseCase: SearchAddressUseCase
) : ViewModel(), OrbitContainerHost<RegisterStoreState, RegisterStoreState, RegisterStoreSideEffect> {
    private val shopId = savedStateHandle.get<Int>(SHOP_ID_KEY)

    override val container = orbitContainer<RegisterStoreState, RegisterStoreSideEffect>(
        initialState = RegisterStoreState(shopId = shopId),
        onCreate = { load() }
    )

    private suspend fun load() = subIntent {
        reduce { state.copy(isLoading = true, error = null) }
        val categories = getShopCategoriesUseCase()
            .getOrElse { return@subIntent showLoadError() }
        val shop = loadShop()
            .getOrElse { return@subIntent showLoadError() }
        val loadedState = shop?.toRegisterStoreState(categories)
            ?: state.copy(categories = categories.toImmutableList())
        reduce { loadedState.copy(isLoading = false, error = null) }
    }

    private suspend fun loadShop(): Result<OwnerShop?> = if (shopId == null) {
        Result.success(null)
    } else {
        getOwnerShopUseCase(shopId)
    }

    private suspend fun showLoadError() = subIntent {
        reduce { state.copy(isLoading = false, error = RegisterStoreError.Load) }
    }

    fun retry() = intent { load() }

    fun onRegistrationStarted() = intent { postSideEffect(RegisterStoreSideEffect.NavigateToCategory) }

    fun onCategorySelected(categoryId: Int) = blockingIntent {
        reduce { state.copy(selectedCategoryId = categoryId) }
    }

    fun onCategoryCompleted() = intent {
        if (!state.isCategoryValid) return@intent
        postSideEffect(RegisterStoreSideEffect.NavigateToBasicInfo)
    }

    fun onStoreNameChanged(value: String) = blockingIntent { reduce { state.copy(storeName = value) } }

    fun onAddressSelected(value: String) = blockingIntent { reduce { state.copy(address = value) } }

    fun searchAddress(keyword: String) = intent {
        if (keyword.isBlank() || state.isAddressSearching) return@intent
        reduce {
            state.copy(
                isAddressSearching = true,
                isAddressSearchError = false
            )
        }
        searchAddressUseCase(keyword)
            .onSuccess { addresses ->
                reduce {
                    state.copy(
                        addressSearchResults = addresses.toImmutableList(),
                        hasAddressSearchResult = true,
                        isAddressSearching = false
                    )
                }
            }.onFailure {
                reduce {
                    state.copy(
                        addressSearchResults = persistentListOf(),
                        hasAddressSearchResult = true,
                        isAddressSearching = false,
                        isAddressSearchError = true
                    )
                }
            }
    }

    fun onBasicInfoCompleted() = intent {
        if (!state.isBasicInfoValid || state.isUploading) return@intent
        postSideEffect(RegisterStoreSideEffect.NavigateToDetailInfo)
    }

    fun onPhoneNumberChanged(value: String) = blockingIntent {
        reduce { state.copy(phoneNumber = value.filter(Char::isDigit).take(11)) }
    }

    fun onDeliveryFeeChanged(value: String) = blockingIntent {
        reduce { state.copy(deliveryFee = value.filter(Char::isDigit)) }
    }

    fun onOtherInfoChanged(value: String) = blockingIntent { reduce { state.copy(otherInfo = value) } }

    fun onDeliveryAvailabilityChanged(value: Boolean) = blockingIntent {
        reduce { state.copy(isDeliveryAvailable = value) }
    }

    fun onCardAvailabilityChanged(value: Boolean) = blockingIntent {
        reduce { state.copy(isCardAvailable = value) }
    }

    fun onBankTransferAvailabilityChanged(value: Boolean) = blockingIntent {
        reduce { state.copy(isBankTransferAvailable = value) }
    }

    fun onOperatingTimesChanged(operatingTimes: ImmutableList<RegisterStoreOperatingTime>) = blockingIntent {
        reduce { state.copy(operatingTimes = operatingTimes) }
    }

    fun onImageUploadRequested(
        fileName: String,
        contentType: String,
        bytes: ByteArray
    ) = intent {
        if (state.isUploading || state.imageUrls.size >= MAX_IMAGE_COUNT) return@intent
        reduce { state.copy(isUploading = true) }
        val result = uploadImageUseCase(
            domain = PreSignedUrlDomain.MARKET,
            contentLength = bytes.size.toLong(),
            contentType = contentType,
            fileName = fileName,
            bytes = bytes
        )
        result
            .onSuccess { applyUploadedImage(it) }
            .onFailure { showImageUploadError() }
    }

    private suspend fun applyUploadedImage(url: String) = subIntent {
        reduce {
            state.copy(
                imageUrls = (state.imageUrls + url).toImmutableList(),
                isUploading = false
            )
        }
    }

    private suspend fun showImageUploadError() = subIntent {
        reduce { state.copy(isUploading = false) }
        postSideEffect(RegisterStoreSideEffect.ShowError(RegisterStoreError.ImageUpload))
    }

    fun onImageRemoved(index: Int) = blockingIntent {
        reduce {
            state.copy(
                imageUrls = state.imageUrls
                    .filterIndexed { i, _ -> i != index }
                    .toImmutableList()
            )
        }
    }

    fun onImageSelectionFailed() = intent {
        postSideEffect(RegisterStoreSideEffect.ShowError(RegisterStoreError.ImageUpload))
    }

    fun onDetailInfoCompleted() = intent {
        if (!state.isDetailInfoValid) return@intent
        postSideEffect(RegisterStoreSideEffect.NavigateToConfirm)
    }

    fun onRegistrationConfirmed() = intent {
        if (!state.isCategoryValid || !state.isBasicInfoValid || !state.isDetailInfoValid) return@intent
        if (state.isSaving || state.isUploading) return@intent
        val categoryId = checkNotNull(state.selectedCategoryId)
        reduce { state.copy(isSaving = true) }
        saveOwnerShopUseCase(shopId, state.toOwnerShopForm(categoryId))
            .onSuccess {
                reduce { state.copy(isSaving = false) }
                postSideEffect(RegisterStoreSideEffect.NavigateToComplete)
            }.onFailure {
                reduce { state.copy(isSaving = false) }
                postSideEffect(RegisterStoreSideEffect.ShowError(RegisterStoreError.Save))
            }
    }

    companion object {
        internal const val SHOP_ID_KEY = "shopId"
        private const val MAX_IMAGE_COUNT = 5
    }
}
