package `in`.koreatech.business

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.domain.usecase.token.ObserveAccessTokenUseCase
import org.orbitmvi.orbit.OrbitContainerHost
import org.orbitmvi.orbit.viewmodel.orbitContainer

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class, binding<ViewModel>())
class AppViewModel(
    private val observeAccessTokenUseCase: ObserveAccessTokenUseCase
) : ViewModel(), OrbitContainerHost<AppState, AppState, Nothing> {
    override val container = orbitContainer<AppState, Nothing>(initialState = AppState.Loading) {
        observeAccessToken()
    }

    private suspend fun observeAccessToken() = subIntent {
        observeAccessTokenUseCase().collect { accessToken ->
            reduce { AppState.Ready(accessToken) }
        }
    }
}
