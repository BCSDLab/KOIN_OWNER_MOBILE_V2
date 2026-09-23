package `in`.koreatech.business.data.source.remote

import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.data.request.store.OwnerShopRequest
import `in`.koreatech.business.data.response.address.AddressSearchResponse
import `in`.koreatech.business.data.response.store.OwnerShopDetailResponse
import `in`.koreatech.business.data.response.store.OwnerShopsResponse
import `in`.koreatech.business.data.response.store.ShopCategoriesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody

@Inject
@SingleIn(AppScope::class)
class OwnerShopRemoteDataSource(
    private val httpClient: HttpClient
) {
    suspend fun searchAddress(keyword: String): AddressSearchResponse =
        httpClient.get("address/search") {
            parameter("keyword", keyword)
            parameter("currentPage", DEFAULT_PAGE)
            parameter("countPerPage", ADDRESS_PAGE_SIZE)
        }.body()

    suspend fun getOwnerShops(): OwnerShopsResponse = httpClient.get("owner/shops").body()

    suspend fun getOwnerShop(shopId: Int): OwnerShopDetailResponse = httpClient.get("owner/shops/$shopId").body()

    suspend fun getShopCategories(): ShopCategoriesResponse = httpClient.get("shops/categories").body()

    suspend fun createOwnerShop(request: OwnerShopRequest) {
        httpClient.post("owner/shops") { setBody(request) }
    }

    suspend fun updateOwnerShop(
        shopId: Int,
        request: OwnerShopRequest
    ) {
        httpClient.put("owner/shops/$shopId") { setBody(request) }
    }

    private companion object {
        const val DEFAULT_PAGE = 1
        const val ADDRESS_PAGE_SIZE = 10
    }
}
