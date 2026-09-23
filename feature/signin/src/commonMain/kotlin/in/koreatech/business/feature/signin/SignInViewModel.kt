package `in`.koreatech.business.feature.signin

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.domain.usecase.auth.SignInUseCase
import `in`.koreatech.business.domain.usecase.token.SaveTokensUseCase
import org.orbitmvi.orbit.OrbitContainerHost
import org.orbitmvi.orbit.blockingIntent
import org.orbitmvi.orbit.viewmodel.orbitContainer

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class, binding<ViewModel>())
class SignInViewModel(
    private val signInUseCase: SignInUseCase,
    private val saveTokensUseCase: SaveTokensUseCase
) : ViewModel(), OrbitContainerHost<SignInState, SignInState, SignInSideEffect> {
    override val container = orbitContainer<SignInState, SignInSideEffect>(SignInState())

    fun updatePhoneNumber(value: String) = blockingIntent {
        reduce { state.copy(phoneNumber = value.filter(Char::isDigit), error = null) }
    }

    fun updatePassword(value: String) = blockingIntent {
        reduce { state.copy(password = value, error = null) }
    }

    fun signIn() = intent {
        if (state.isLoading) return@intent
        if (state.phoneNumber.length != 11 || state.password.isBlank()) {
            reduce { state.copy(error = SignInError.Required) }
            return@intent
        }
        reduce { state.copy(isLoading = true, error = null) }
        signInUseCase(state.phoneNumber, state.password)
            .onSuccess { tokens ->
                saveTokensUseCase(tokens.accessToken, tokens.refreshToken)
                reduce { state.copy(isLoading = false) }
                postSideEffect(SignInSideEffect.SignInSuccess)
            }.onFailure {
                reduce { state.copy(isLoading = false, error = SignInError.Failed) }
            }
    }
}
