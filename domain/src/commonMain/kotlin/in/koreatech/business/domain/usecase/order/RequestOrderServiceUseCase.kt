package `in`.koreatech.business.domain.usecase.order

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.model.order.OrderServiceApplication
import `in`.koreatech.business.domain.repository.OwnerOrderRepository

@Inject
class RequestOrderServiceUseCase(private val repository: OwnerOrderRepository) {
    suspend operator fun invoke(shopId: Int, application: OrderServiceApplication): Result<Unit> =
        repository.requestOrderService(shopId, application)
}
