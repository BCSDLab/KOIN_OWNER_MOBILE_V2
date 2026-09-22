package `in`.koreatech.business.feature.signup

import `in`.koreatech.business.domain.model.store.AttachStore
import `in`.koreatech.business.domain.model.store.StoreSearchResult
import `in`.koreatech.business.domain.model.store.StoreUrl
import `in`.koreatech.business.feature.signup.verification.PhoneNumberVerificationState
import `in`.koreatech.business.feature.signup.verification.VerificationCodeState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SignupState(
    val agreedToService: Boolean = false,
    val agreedToPrivacy: Boolean = false,
    val agreedToMarketing: Boolean = false,
    val phoneNumber: String = "",
    val verificationCode: String = "",
    val name: String = "",
    val password: String = "",
    val passwordConfirmation: String = "",
    val businessNumber: String = "",
    val storeName: String = "",
    val storePhoneNumber: String = "",
    val storeSearchQuery: String = "",
    val storeSearchResults: ImmutableList<StoreSearchResult> = persistentListOf(),
    val selectedStoreId: Int? = null,
    val isSearchingStores: Boolean = false,
    val selectedImages: ImmutableList<AttachStore> = persistentListOf(),
    val fileInfo: ImmutableList<StoreUrl> = persistentListOf(),
    val privacyTerm: String = "",
    val koinTerm: String = "",
    val marketingTerm: String = "",
    val error: SignupError? = null,
    val isLoading: Boolean = false,
    val isUploading: Boolean = false,
    val isVerificationCodeSent: Boolean = false,
    val phoneNumberVerificationState: PhoneNumberVerificationState = PhoneNumberVerificationState.None,
    val verificationCodeState: VerificationCodeState = VerificationCodeState.None,
    val verificationToken: String? = null
) {
    val termsAgreed: Boolean
        get() = agreedToService && agreedToPrivacy && agreedToMarketing
}
