package `in`.koreatech.business.feature.signin

import androidx.compose.foundation.text.input.TextFieldState

data class SignInState(
    val phoneNumberTextFieldState: TextFieldState = TextFieldState(),
    val passwordTextFieldState: TextFieldState = TextFieldState(),
    val isLoading: Boolean = false,
    val error: SignInError? = null
) {
    val phoneNumber: String get() = phoneNumberTextFieldState.text.toString()
    val password: String get() = passwordTextFieldState.text.toString()
}
