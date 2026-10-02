package `in`.koreatech.business.feature.signup

import androidx.compose.foundation.text.input.TextFieldState
import `in`.koreatech.business.feature.signup.model.SignupAttachment
import `in`.koreatech.business.feature.signup.model.SignupStoreSearchResult
import `in`.koreatech.business.feature.signup.model.SignupStoreUrl
import `in`.koreatech.business.feature.signup.verification.PhoneNumberVerificationState
import `in`.koreatech.business.feature.signup.verification.VerificationCodeState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SignupState(
    val phoneNumberTextFieldState: TextFieldState = TextFieldState(),
    val verificationCodeTextFieldState: TextFieldState = TextFieldState(),
    val nameTextFieldState: TextFieldState = TextFieldState(),
    val passwordTextFieldState: TextFieldState = TextFieldState(),
    val passwordConfirmationTextFieldState: TextFieldState = TextFieldState(),
    val businessNumberTextFieldState: TextFieldState = TextFieldState(),
    val storeNameTextFieldState: TextFieldState = TextFieldState(),
    val storePhoneNumberTextFieldState: TextFieldState = TextFieldState(),
    val storeSearchQueryTextFieldState: TextFieldState = TextFieldState(),
    val agreedToService: Boolean = false,
    val agreedToPrivacy: Boolean = false,
    val agreedToMarketing: Boolean = false,
    val storeSearchResults: ImmutableList<SignupStoreSearchResult> = persistentListOf(),
    val selectedStoreId: Int? = null,
    val isSearchingStores: Boolean = false,
    val selectedImages: ImmutableList<SignupAttachment> = persistentListOf(),
    val fileInfo: ImmutableList<SignupStoreUrl> = persistentListOf(),
    val pendingImageCount: Int = 0,
    val privacyTerm: String = "",
    val koinTerm: String = "",
    val marketingTerm: String = "",
    val error: SignupError? = null,
    val isLoading: Boolean = false,
    val isVerificationCodeSent: Boolean = false,
    val phoneNumberVerificationState: PhoneNumberVerificationState = PhoneNumberVerificationState.None,
    val verificationCodeState: VerificationCodeState = VerificationCodeState.None,
    val verificationToken: String? = null
) {
    val phoneNumber: String get() = phoneNumberTextFieldState.text.toString()
    val verificationCode: String get() = verificationCodeTextFieldState.text.toString()
    val name: String get() = nameTextFieldState.text.toString()
    val password: String get() = passwordTextFieldState.text.toString()
    val passwordConfirmation: String get() = passwordConfirmationTextFieldState.text.toString()
    val businessNumber: String get() = businessNumberTextFieldState.text.toString()
    val storeName: String get() = storeNameTextFieldState.text.toString()
    val storePhoneNumber: String get() = storePhoneNumberTextFieldState.text.toString()
    val storeSearchQuery: String get() = storeSearchQueryTextFieldState.text.toString()

    val termsAgreed: Boolean
        get() = agreedToService && agreedToPrivacy && agreedToMarketing

    val isUploading: Boolean
        get() = pendingImageCount > 0
}
