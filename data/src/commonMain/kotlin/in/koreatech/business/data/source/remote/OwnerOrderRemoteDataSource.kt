package `in`.koreatech.business.data.source.remote

import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.data.response.order.OwnerOrderDetailResponse
import `in`.koreatech.business.data.response.order.OwnerOrderableShopsResponse
import `in`.koreatech.business.data.response.order.OwnerOrdersResponse
import `in`.koreatech.business.domain.model.order.OwnerOrderCategory
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

@Inject
@SingleIn(AppScope::class)
class OwnerOrderRemoteDataSource(private val httpClient: HttpClient) {
    suspend fun getOrderableShops(): OwnerOrderableShopsResponse = httpClient.get("owner/order/shops").body()

    suspend fun getOrders(orderableShopId: Int, category: OwnerOrderCategory): OwnerOrdersResponse =
        httpClient.get("owner/order/shop/$orderableShopId/orders") { parameter("status", category.name) }.body()

    suspend fun getOrder(orderableShopId: Int, orderId: Int): OwnerOrderDetailResponse =
        httpClient.get("owner/order/shop/$orderableShopId/orders/$orderId").body()
}
