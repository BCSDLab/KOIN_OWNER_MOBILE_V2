package `in`.koreatech.business.feature.signin.password

import androidx.compose.foundation.text.input.TextFieldState

data class PasswordResetState(
    val phoneNumberTextFieldState: TextFieldState = TextFieldState(),
    val codeTextFieldState: TextFieldState = TextFieldState(),
    val passwordTextFieldState: TextFieldState = TextFieldState(),
    val passwordConfirmationTextFieldState: TextFieldState = TextFieldState(),
    val step: PasswordResetStep = PasswordResetStep.Verification,
    val isCodeSent: Boolean = false,
    val isLoading: Boolean = false,
    val error: PasswordResetError? = null
) {
    val phoneNumber: String get() = phoneNumberTextFieldState.text.toString()
    val code: String get() = codeTextFieldState.text.toString()
    val password: String get() = passwordTextFieldState.text.toString()
    val passwordConfirmation: String get() = passwordConfirmationTextFieldState.text.toString()
}
