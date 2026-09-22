package `in`.koreatech.business.feature.signin

sealed interface SignInSideEffect {
    data object SignInSuccess : SignInSideEffect
}
