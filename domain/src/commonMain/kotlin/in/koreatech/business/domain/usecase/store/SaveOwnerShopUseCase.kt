package `in`.koreatech.business.domain.usecase.store

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.model.store.OwnerShopForm
import `in`.koreatech.business.domain.repository.OwnerShopRepository

@Inject
class SaveOwnerShopUseCase(
    private val repository: OwnerShopRepository
) {
    suspend operator fun invoke(
        shopId: Int?,
        shop: OwnerShopForm
    ): Result<Unit> = if (shopId == null) repository.createOwnerShop(shop) else repository.updateOwnerShop(shopId, shop)
}
