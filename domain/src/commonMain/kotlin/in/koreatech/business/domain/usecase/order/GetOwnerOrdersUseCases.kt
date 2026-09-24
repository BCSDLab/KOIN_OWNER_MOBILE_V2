package `in`.koreatech.business.domain.usecase.order

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.model.order.OwnerOrderCategory
import `in`.koreatech.business.domain.repository.OwnerOrderRepository

@Inject
class GetOwnerOrderableShopsUseCase(private val repository: OwnerOrderRepository) {
    suspend operator fun invoke() = repository.getOrderableShops()
}

@Inject
class GetOwnerOrdersUseCase(private val repository: OwnerOrderRepository) {
    suspend operator fun invoke(orderableShopId: Int, category: OwnerOrderCategory) =
        repository.getOrders(orderableShopId, category)
}

@Inject
class GetOwnerOrderUseCase(private val repository: OwnerOrderRepository) {
    suspend operator fun invoke(orderableShopId: Int, orderId: Int) = repository.getOrder(orderableShopId, orderId)
}
