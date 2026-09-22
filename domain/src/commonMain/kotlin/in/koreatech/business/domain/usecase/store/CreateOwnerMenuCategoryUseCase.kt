package `in`.koreatech.business.domain.usecase.store

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.repository.OwnerMenuRepository

@Inject
class CreateOwnerMenuCategoryUseCase(
    private val repository: OwnerMenuRepository
) {
    suspend operator fun invoke(
        shopId: Int,
        name: String
    ): Result<Unit> = repository.createOwnerMenuCategory(shopId, name)
}
