package `in`.koreatech.business.feature.store.manage

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.domain.usecase.store.GetOwnerShopsUseCase
import `in`.koreatech.business.domain.usecase.store.ObserveSelectedShopIdUseCase
import `in`.koreatech.business.domain.usecase.store.SetSelectedShopUseCase
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.OrbitContainerHost
import org.orbitmvi.orbit.viewmodel.orbitContainer

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class, binding<ViewModel>())
class ManageShopsViewModel(
    private val getOwnerShopsUseCase: GetOwnerShopsUseCase,
    private val observeSelectedShopIdUseCase: ObserveSelectedShopIdUseCase,
    private val setSelectedShopUseCase: SetSelectedShopUseCase
) : ViewModel(), OrbitContainerHost<ManageShopsState, ManageShopsState, ManageShopsSideEffect> {
    override val container =
        orbitContainer<ManageShopsState, ManageShopsSideEffect>(
            initialState = ManageShopsState(),
            onCreate = { initialize() }
        )

    private var hasResumed = false

    fun onResume() {
        if (hasResumed) {
            retry()
        } else {
            hasResumed = true
        }
    }

    fun selectShop(shopId: Int) =
        intent {
            setSelectedShopUseCase(shopId)
        }

    fun retry() = intent { loadShops() }

    private suspend fun initialize() =
        coroutineScope {
            launch { observeSelectedShop() }
            launch { loadShops() }
        }

    private suspend fun loadShops() =
        subIntent {
            getOwnerShopsUseCase()
                .onStart { reduce { state.copy(isLoading = true) } }
                .onEach { result ->
                    result
                        .onSuccess { shops ->
                            val selectedShopId =
                                state.selectedShopId
                                    ?.takeIf { shopId -> shops.any { it.id == shopId } }
                                    ?: shops.firstOrNull()?.id
                            setSelectedShopUseCase(selectedShopId)
                            reduce { state.copy(shops = shops.toImmutableList(), isLoading = false) }
                        }.onFailure {
                            reduce { state.copy(isLoading = false) }
                            postSideEffect(ManageShopsSideEffect.ShopLoadFailed)
                        }
                }.collect()
        }

    private suspend fun observeSelectedShop() =
        subIntent {
            observeSelectedShopIdUseCase().collectLatest { shopId ->
                reduce { state.copy(selectedShopId = shopId) }
            }
        }
}
