package `in`.koreatech.business.domain.usecase.store

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.repository.OwnerMenuRepository

@Inject
class DeleteOwnerMenuUseCase(
    private val repository: OwnerMenuRepository
) {
    suspend operator fun invoke(menuId: Int): Result<Unit> = repository.deleteOwnerMenu(menuId)
}
