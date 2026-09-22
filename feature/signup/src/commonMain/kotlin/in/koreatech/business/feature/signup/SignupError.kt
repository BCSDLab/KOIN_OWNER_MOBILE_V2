package `in`.koreatech.business.feature.signup

sealed interface SignupError {
    data object PhoneVerificationRequired : SignupError

    data object FileUpload : SignupError

    data object BusinessNumberCheck : SignupError

    data object StoreSearch : SignupError

    data object Submit : SignupError

    data class Dynamic(
        val message: String
    ) : SignupError
}
