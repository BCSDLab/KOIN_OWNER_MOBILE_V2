package `in`.koreatech.business.feature.signin.password

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.domain.usecase.auth.ResetPasswordUseCase
import `in`.koreatech.business.domain.usecase.auth.SendPasswordResetSmsUseCase
import `in`.koreatech.business.domain.usecase.auth.VerifyPasswordResetSmsUseCase
import org.orbitmvi.orbit.OrbitContainerHost
import org.orbitmvi.orbit.blockingIntent
import org.orbitmvi.orbit.viewmodel.orbitContainer

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class, binding<ViewModel>())
class PasswordResetViewModel(
    private val sendPasswordResetSmsUseCase: SendPasswordResetSmsUseCase,
    private val verifyPasswordResetSmsUseCase: VerifyPasswordResetSmsUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel(), OrbitContainerHost<PasswordResetState, PasswordResetState, Nothing> {
    override val container = orbitContainer<PasswordResetState, Nothing>(PasswordResetState())

    fun updatePhoneNumber(value: String) = blockingIntent {
        reduce {
            state.copy(
                phoneNumber = value.filter(Char::isDigit).take(11),
                code = "",
                isCodeSent = false,
                error = null
            )
        }
    }

    fun updateCode(value: String) = blockingIntent {
        reduce { state.copy(code = value.filter(Char::isDigit).take(6), error = null) }
    }

    fun updatePassword(value: String) = blockingIntent {
        reduce { state.copy(password = value.take(18), error = null) }
    }

    fun updatePasswordConfirmation(value: String) = blockingIntent {
        reduce { state.copy(passwordConfirmation = value.take(18), error = null) }
    }

    fun sendCode() = intent {
        if (state.phoneNumber.length != 11) {
            reduce { state.copy(error = PasswordResetError.InvalidPhone) }
            return@intent
        }
        if (state.isLoading) return@intent
        reduce { state.copy(isLoading = true, error = null) }
        sendPasswordResetSmsUseCase(state.phoneNumber)
            .onSuccess { reduce { state.copy(isLoading = false, isCodeSent = true) } }
            .onFailure { reduce { state.copy(isLoading = false, error = PasswordResetError.Send) } }
    }

    fun verifyCode() = intent {
        if (!state.isCodeSent || state.code.length != 6 || state.isLoading) return@intent
        reduce { state.copy(isLoading = true, error = null) }
        verifyPasswordResetSmsUseCase(state.phoneNumber, state.code)
            .onSuccess {
                reduce { state.copy(step = PasswordResetStep.Password, isLoading = false) }
            }.onFailure {
                reduce { state.copy(isLoading = false, error = PasswordResetError.InvalidCode) }
            }
    }

    fun resetPassword() = intent {
        if (state.password.length !in 6..18 || state.password != state.passwordConfirmation) {
            reduce { state.copy(error = PasswordResetError.PasswordMismatch) }
            return@intent
        }
        if (state.isLoading) return@intent
        reduce { state.copy(isLoading = true, error = null) }
        resetPasswordUseCase(state.phoneNumber, state.password)
            .onSuccess {
                reduce { state.copy(step = PasswordResetStep.Complete, isLoading = false) }
            }.onFailure {
                reduce { state.copy(isLoading = false, error = PasswordResetError.Reset) }
            }
    }
}
