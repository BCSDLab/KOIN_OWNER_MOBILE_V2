package `in`.koreatech.business.feature.signin

data class SignInState(
    val phoneNumber: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: SignInError? = null
)
