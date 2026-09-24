package `in`.koreatech.business.feature.signup

import `in`.koreatech.business.feature.signup.model.SignupAttachment
import `in`.koreatech.business.feature.signup.model.SignupStoreSearchResult
import `in`.koreatech.business.feature.signup.model.SignupStoreUrl
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
    val storeSearchResults: ImmutableList<SignupStoreSearchResult> = persistentListOf(),
    val selectedStoreId: Int? = null,
    val isSearchingStores: Boolean = false,
    val selectedImages: ImmutableList<SignupAttachment> = persistentListOf(),
    val fileInfo: ImmutableList<SignupStoreUrl> = persistentListOf(),
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
