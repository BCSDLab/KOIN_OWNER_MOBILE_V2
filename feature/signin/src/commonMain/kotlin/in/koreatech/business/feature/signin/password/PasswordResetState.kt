package `in`.koreatech.business.feature.signin.password

data class PasswordResetState(
    val step: PasswordResetStep = PasswordResetStep.Verification,
    val phoneNumber: String = "",
    val code: String = "",
    val password: String = "",
    val passwordConfirmation: String = "",
    val isCodeSent: Boolean = false,
    val isLoading: Boolean = false,
    val error: PasswordResetError? = null
)
