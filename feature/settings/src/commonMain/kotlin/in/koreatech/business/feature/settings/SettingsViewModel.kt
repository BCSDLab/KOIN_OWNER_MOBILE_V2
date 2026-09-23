package `in`.koreatech.business.feature.settings

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.domain.usecase.auth.DeleteOwnerUseCase
import `in`.koreatech.business.domain.usecase.token.SignOutUseCase
import org.orbitmvi.orbit.OrbitContainerHost
import org.orbitmvi.orbit.blockingIntent
import org.orbitmvi.orbit.viewmodel.orbitContainer

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class, binding<ViewModel>())
class SettingsViewModel(
    private val signOutUseCase: SignOutUseCase,
    private val deleteOwnerUseCase: DeleteOwnerUseCase
) : ViewModel(), OrbitContainerHost<SettingsState, SettingsState, SettingsSideEffect> {
    override val container = orbitContainer<SettingsState, SettingsSideEffect>(SettingsState())

    fun signOut() = intent {
        signOutUseCase()
    }

    fun showDeleteOwnerDialog() = blockingIntent {
        reduce { state.copy(isDeleteOwnerDialogVisible = true) }
    }

    fun dismissDeleteOwnerDialog() = blockingIntent {
        if (!state.isDeletingOwner) {
            reduce { state.copy(isDeleteOwnerDialogVisible = false) }
        }
    }

    fun deleteOwner() = intent {
        if (state.isDeletingOwner) return@intent
        reduce { state.copy(isDeletingOwner = true) }
        deleteOwnerUseCase()
            .onSuccess {
                reduce {
                    state.copy(
                        isDeleteOwnerDialogVisible = false,
                        isDeletingOwner = false
                    )
                }
            }.onFailure {
                reduce { state.copy(isDeletingOwner = false) }
                postSideEffect(SettingsSideEffect.DeleteOwnerFailed)
            }
    }
}
