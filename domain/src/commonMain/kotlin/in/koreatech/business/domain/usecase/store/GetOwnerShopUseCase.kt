package `in`.koreatech.business.domain.usecase.store

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.model.store.OwnerShop
import `in`.koreatech.business.domain.repository.OwnerShopRepository

@Inject
class GetOwnerShopUseCase(
    private val repository: OwnerShopRepository
) {
    suspend operator fun invoke(shopId: Int): Result<OwnerShop> = repository.getOwnerShop(shopId)
}
