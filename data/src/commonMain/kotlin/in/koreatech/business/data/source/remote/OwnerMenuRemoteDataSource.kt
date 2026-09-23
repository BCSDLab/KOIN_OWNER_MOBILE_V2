package `in`.koreatech.business.data.source.remote

import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.data.request.store.CreateOwnerMenuCategoryRequest
import `in`.koreatech.business.data.request.store.ModifyOwnerMenuCategoryRequest
import `in`.koreatech.business.data.request.store.OwnerMenuRequest
import `in`.koreatech.business.data.response.store.OwnerMenuCategoriesResponse
import `in`.koreatech.business.data.response.store.OwnerMenuDetailResponse
import `in`.koreatech.business.data.response.store.OwnerMenuResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody

@Inject
@SingleIn(AppScope::class)
class OwnerMenuRemoteDataSource(
    private val httpClient: HttpClient
) {
    suspend fun getOwnerMenus(shopId: Int): OwnerMenuResponse = httpClient
        .get("owner/shops/menus") {
            parameter("shopId", shopId)
        }.body()

    suspend fun getOwnerMenu(menuId: Int): OwnerMenuDetailResponse = httpClient.get("owner/shops/menus/$menuId").body()

    suspend fun getOwnerMenuCategories(shopId: Int): OwnerMenuCategoriesResponse = httpClient
        .get("owner/shops/menus/categories") {
            parameter("shopId", shopId)
        }.body()

    suspend fun createOwnerMenu(
        shopId: Int,
        request: OwnerMenuRequest
    ) {
        httpClient.post("owner/shops/$shopId/menus") { setBody(request) }
    }

    suspend fun updateOwnerMenu(
        menuId: Int,
        request: OwnerMenuRequest
    ) {
        httpClient.put("owner/shops/menus/$menuId") { setBody(request) }
    }

    suspend fun deleteOwnerMenu(menuId: Int) {
        httpClient.delete("owner/shops/menus/$menuId")
    }

    suspend fun createOwnerMenuCategory(
        shopId: Int,
        request: CreateOwnerMenuCategoryRequest
    ) {
        httpClient.post("owner/shops/$shopId/menus/categories") { setBody(request) }
    }

    suspend fun updateOwnerMenuCategory(
        categoryId: Int,
        request: ModifyOwnerMenuCategoryRequest
    ) {
        httpClient.put("owner/shops/menus/categories/$categoryId") { setBody(request) }
    }

    suspend fun deleteOwnerMenuCategory(categoryId: Int) {
        httpClient.delete("owner/shops/menus/categories/$categoryId")
    }
}
