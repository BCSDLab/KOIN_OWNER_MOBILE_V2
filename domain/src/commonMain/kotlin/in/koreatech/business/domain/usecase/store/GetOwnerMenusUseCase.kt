package `in`.koreatech.business.domain.usecase.store

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.model.store.OwnerMenuCategory
import `in`.koreatech.business.domain.repository.OwnerMenuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Inject
class GetOwnerMenusUseCase(
    private val repository: OwnerMenuRepository
) {
    operator fun invoke(shopId: Int): Flow<Result<List<OwnerMenuCategory>>> =
        flow {
            emit(repository.getOwnerMenus(shopId))
        }
}
