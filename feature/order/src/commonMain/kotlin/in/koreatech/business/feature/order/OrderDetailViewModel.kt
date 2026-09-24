package `in`.koreatech.business.feature.order

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.domain.usecase.order.GetOwnerOrderUseCase
import org.orbitmvi.orbit.OrbitContainerHost
import org.orbitmvi.orbit.viewmodel.orbitContainer

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class, binding<ViewModel>())
class OrderDetailViewModel(
    private val getOrderUseCase: GetOwnerOrderUseCase
) : ViewModel(), OrbitContainerHost<OrderDetailState, OrderDetailState, Nothing> {
    override val container = orbitContainer<OrderDetailState, Nothing>(OrderDetailState())
    fun load(orderableShopId: Int, orderId: Int) = intent {
        reduce { state.copy(isLoading = true, hasError = false) }
        getOrderUseCase(orderableShopId, orderId)
            .onSuccess { reduce { state.copy(order = it, isLoading = false) } }
            .onFailure { reduce { state.copy(isLoading = false, hasError = true) } }
    }
}
