package `in`.koreatech.business.data.repository

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.data.mapper.toOwnerShop
import `in`.koreatech.business.data.mapper.toOwnerShopRequest
import `in`.koreatech.business.data.mapper.toShopCategory
import `in`.koreatech.business.data.source.remote.OwnerShopRemoteDataSource
import `in`.koreatech.business.data.util.suspendRunCatching
import `in`.koreatech.business.domain.model.store.OwnerShop
import `in`.koreatech.business.domain.model.store.OwnerShopForm
import `in`.koreatech.business.domain.model.store.ShopCategory
import `in`.koreatech.business.domain.repository.OwnerShopRepository

@ContributesBinding(AppScope::class)
@Inject
@SingleIn(AppScope::class)
class OwnerShopRepositoryImpl(
    private val ownerShopRemoteDataSource: OwnerShopRemoteDataSource
) : OwnerShopRepository {
    override suspend fun getOwnerShops(): Result<List<OwnerShop>> =
        suspendRunCatching {
            ownerShopRemoteDataSource.getOwnerShops().shops.map { summary ->
                ownerShopRemoteDataSource.getOwnerShop(summary.id).toOwnerShop(summary)
            }
        }

    override suspend fun getOwnerShop(shopId: Int): Result<OwnerShop> =
        suspendRunCatching {
            ownerShopRemoteDataSource.getOwnerShop(shopId).toOwnerShop()
        }

    override suspend fun getShopCategories(): Result<List<ShopCategory>> =
        suspendRunCatching {
            ownerShopRemoteDataSource.getShopCategories().categories.map { it.toShopCategory() }
        }

    override suspend fun createOwnerShop(shop: OwnerShopForm): Result<Unit> =
        suspendRunCatching {
            ownerShopRemoteDataSource.createOwnerShop(shop.toOwnerShopRequest())
        }

    override suspend fun updateOwnerShop(
        shopId: Int,
        shop: OwnerShopForm
    ): Result<Unit> =
        suspendRunCatching {
            ownerShopRemoteDataSource.updateOwnerShop(shopId, shop.toOwnerShopRequest())
        }
}
