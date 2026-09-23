package `in`.koreatech.business.domain.repository

import `in`.koreatech.business.domain.model.address.AddressSearchResult
import `in`.koreatech.business.domain.model.store.OwnerShop
import `in`.koreatech.business.domain.model.store.OwnerShopForm
import `in`.koreatech.business.domain.model.store.ShopCategory

interface OwnerShopRepository {
    suspend fun searchAddress(keyword: String): Result<List<AddressSearchResult>>

    suspend fun getOwnerShops(): Result<List<OwnerShop>>

    suspend fun getOwnerShop(shopId: Int): Result<OwnerShop>

    suspend fun getShopCategories(): Result<List<ShopCategory>>

    suspend fun createOwnerShop(shop: OwnerShopForm): Result<Unit>

    suspend fun updateOwnerShop(
        shopId: Int,
        shop: OwnerShopForm
    ): Result<Unit>
}
