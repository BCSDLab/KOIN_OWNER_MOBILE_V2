package `in`.koreatech.business.domain.repository

import `in`.koreatech.business.domain.model.order.OwnerOrder
import `in`.koreatech.business.domain.model.order.OwnerOrderCategory
import `in`.koreatech.business.domain.model.order.OwnerOrderDetail
import `in`.koreatech.business.domain.model.order.OwnerOrderableShop

interface OwnerOrderRepository {
    suspend fun getOrderableShops(): Result<List<OwnerOrderableShop>>

    suspend fun getOrders(orderableShopId: Int, category: OwnerOrderCategory): Result<List<OwnerOrder>>

    suspend fun getOrder(orderableShopId: Int, orderId: Int): Result<OwnerOrderDetail>
}
