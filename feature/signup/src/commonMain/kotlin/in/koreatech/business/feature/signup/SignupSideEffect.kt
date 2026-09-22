package `in`.koreatech.business.feature.signup

sealed interface SignupSideEffect {
    data object NavigateToAccount : SignupSideEffect

    data object NavigateToPassword : SignupSideEffect

    data object NavigateToBusiness : SignupSideEffect

    data object NavigateToStore : SignupSideEffect

    data object NavigateToAttachments : SignupSideEffect

    data object NavigateToComplete : SignupSideEffect

    data object NavigateBackFromStoreSearch : SignupSideEffect

    data object CompleteSignup : SignupSideEffect
}
