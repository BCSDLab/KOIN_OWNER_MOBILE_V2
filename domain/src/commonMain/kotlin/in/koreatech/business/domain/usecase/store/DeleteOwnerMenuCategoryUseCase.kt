package `in`.koreatech.business.domain.usecase.store

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.repository.OwnerMenuRepository

@Inject
class DeleteOwnerMenuCategoryUseCase(
    private val repository: OwnerMenuRepository
) {
    suspend operator fun invoke(categoryId: Int): Result<Unit> = repository.deleteOwnerMenuCategory(categoryId)
}
