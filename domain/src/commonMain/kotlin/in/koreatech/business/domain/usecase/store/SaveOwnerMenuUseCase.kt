package `in`.koreatech.business.domain.usecase.store

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.model.store.OwnerMenuForm
import `in`.koreatech.business.domain.repository.OwnerMenuRepository

@Inject
class SaveOwnerMenuUseCase(
    private val repository: OwnerMenuRepository
) {
    suspend operator fun invoke(
        shopId: Int,
        menuId: Int?,
        menu: OwnerMenuForm
    ): Result<Unit> =
        if (menuId == null) {
            repository.createOwnerMenu(shopId, menu)
        } else {
            repository.updateOwnerMenu(menuId, menu)
        }
}
