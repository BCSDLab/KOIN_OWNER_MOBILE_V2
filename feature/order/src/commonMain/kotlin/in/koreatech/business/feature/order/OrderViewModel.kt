package `in`.koreatech.business.feature.order

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.domain.model.order.OwnerOrderCategory
import `in`.koreatech.business.domain.usecase.order.GetOwnerOrderableShopsUseCase
import `in`.koreatech.business.domain.usecase.order.GetOwnerOrdersUseCase
import `in`.koreatech.business.domain.usecase.store.ObserveSelectedShopUseCase
import `in`.koreatech.business.feature.order.model.toOrderUiModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import org.orbitmvi.orbit.OrbitContainerHost
import org.orbitmvi.orbit.viewmodel.orbitContainer

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class, binding<ViewModel>())
class OrderViewModel(
    private val observeSelectedShopUseCase: ObserveSelectedShopUseCase,
    private val getOrderableShopsUseCase: GetOwnerOrderableShopsUseCase,
    private val getOrdersUseCase: GetOwnerOrdersUseCase
) : ViewModel(), OrbitContainerHost<OrderState, OrderState, Nothing> {
    override val container = orbitContainer<OrderState, Nothing>(OrderState()) {
        observeShop()
    }

    private suspend fun observeShop() = subIntent {
        observeSelectedShopUseCase().collectLatest { result ->
            result.onFailure {
                reduce { state.copy(isLoading = false, hasError = true) }
                return@collectLatest
            }
            val shop = result.getOrNull()
            val orderableShopId = getOrderableShopsUseCase().getOrElse {
                reduce { state.copy(isLoading = false, hasError = true) }
                return@collectLatest
            }.firstOrNull { it.shopId == shop?.id }?.id
            reduce { state.copy(shopName = shop?.name, orderableShopId = orderableShopId) }
            loadOrders(orderableShopId, state.category)
        }
    }

    fun selectCategory(category: OwnerOrderCategory) = intent {
        if (category == state.category) return@intent
        reduce { state.copy(category = category) }
        loadOrders(state.orderableShopId, category)
    }

    fun retry() = intent { loadOrders(state.orderableShopId, state.category) }

    private suspend fun loadOrders(orderableShopId: Int?, category: OwnerOrderCategory) = subIntent {
        if (orderableShopId == null) {
            reduce { state.copy(orders = persistentListOf(), isLoading = false, hasError = false) }
            return@subIntent
        }
        reduce { state.copy(isLoading = true, hasError = false) }
        getOrdersUseCase(orderableShopId, category)
            .onSuccess {
                reduce {
                    state.copy(
                        orders = it.map { order -> order.toOrderUiModel() }.toImmutableList(),
                        isLoading = false
                    )
                }
            }
            .onFailure { reduce { state.copy(isLoading = false, hasError = true) } }
    }
}
