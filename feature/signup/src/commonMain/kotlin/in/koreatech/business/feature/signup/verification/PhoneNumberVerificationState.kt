package `in`.koreatech.business.feature.signup.verification

sealed interface PhoneNumberVerificationState {
    data object None : PhoneNumberVerificationState

    data object WrongFormat : PhoneNumberVerificationState

    data object AlreadySignedUp : PhoneNumberVerificationState

    data object Sent : PhoneNumberVerificationState

    data class Failed(
        val message: String?
    ) : PhoneNumberVerificationState
}
