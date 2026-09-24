package `in`.koreatech.business.data.repository

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.data.mapper.toDomain
import `in`.koreatech.business.data.source.remote.OwnerOrderRemoteDataSource
import `in`.koreatech.business.data.util.suspendRunCatching
import `in`.koreatech.business.domain.model.order.OwnerOrder
import `in`.koreatech.business.domain.model.order.OwnerOrderCategory
import `in`.koreatech.business.domain.model.order.OwnerOrderDetail
import `in`.koreatech.business.domain.model.order.OwnerOrderableShop
import `in`.koreatech.business.domain.repository.OwnerOrderRepository

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class OwnerOrderRepositoryImpl(private val remoteDataSource: OwnerOrderRemoteDataSource) : OwnerOrderRepository {
    override suspend fun getOrderableShops(): Result<List<OwnerOrderableShop>> = suspendRunCatching {
        remoteDataSource.getOrderableShops().shops.map { it.toDomain() }
    }

    override suspend fun getOrders(orderableShopId: Int, category: OwnerOrderCategory): Result<List<OwnerOrder>> =
        suspendRunCatching { remoteDataSource.getOrders(orderableShopId, category).orders.map { it.toDomain() } }

    override suspend fun getOrder(orderableShopId: Int, orderId: Int): Result<OwnerOrderDetail> =
        suspendRunCatching { remoteDataSource.getOrder(orderableShopId, orderId).toDomain() }
}
