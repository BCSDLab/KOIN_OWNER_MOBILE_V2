package `in`.koreatech.business.feature.signup.verification

sealed interface VerificationCodeState {
    data object None : VerificationCodeState

    data object Valid : VerificationCodeState

    data object NotValid : VerificationCodeState
}
