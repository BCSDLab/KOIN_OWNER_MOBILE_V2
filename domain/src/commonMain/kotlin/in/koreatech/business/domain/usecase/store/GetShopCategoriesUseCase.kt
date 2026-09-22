package `in`.koreatech.business.domain.usecase.store

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.model.store.ShopCategory
import `in`.koreatech.business.domain.repository.OwnerShopRepository

@Inject
class GetShopCategoriesUseCase(
    private val repository: OwnerShopRepository
) {
    suspend operator fun invoke(): Result<List<ShopCategory>> = repository.getShopCategories()
}
