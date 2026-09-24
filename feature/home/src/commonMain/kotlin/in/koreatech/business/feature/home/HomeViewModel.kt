package `in`.koreatech.business.feature.home

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.domain.usecase.store.GetSelectedShopUseCase
import `in`.koreatech.business.feature.home.model.toHomeShop
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.OrbitContainerHost
import org.orbitmvi.orbit.viewmodel.orbitContainer

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class, binding<ViewModel>())
class HomeViewModel(
    private val getSelectedShopUseCase: GetSelectedShopUseCase
) : ViewModel(), OrbitContainerHost<HomeState, HomeState, HomeSideEffect> {
    override val container = orbitContainer<HomeState, HomeSideEffect>(HomeState(), onCreate = { loadShop() })

    private var hasResumed = false

    fun onResume() {
        if (hasResumed) {
            loadShops()
        } else {
            hasResumed = true
        }
    }

    fun loadShops() = intent { loadShop() }

    private suspend fun loadShop() = subIntent {
        getSelectedShopUseCase()
            .onStart { reduce { state.copy(isLoading = true) } }
            .onEach { result ->
                result
                    .onSuccess { shop ->
                        reduce { state.copy(shop = shop?.toHomeShop(), isLoading = false) }
                    }.onFailure {
                        reduce { state.copy(isLoading = false) }
                        postSideEffect(HomeSideEffect.ShopLoadFailed)
                    }
            }.collect()
    }
}
