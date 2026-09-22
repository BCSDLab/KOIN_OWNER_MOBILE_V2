package `in`.koreatech.business.domain.usecase.store

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.repository.OwnerMenuRepository

@Inject
class UpdateOwnerMenuCategoryUseCase(
    private val repository: OwnerMenuRepository
) {
    suspend operator fun invoke(
        categoryId: Int,
        name: String
    ): Result<Unit> = repository.updateOwnerMenuCategory(categoryId, name)
}
