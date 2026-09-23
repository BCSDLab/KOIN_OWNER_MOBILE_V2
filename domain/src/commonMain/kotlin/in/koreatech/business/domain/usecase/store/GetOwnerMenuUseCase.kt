package `in`.koreatech.business.domain.usecase.store

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.model.store.OwnerMenuDetail
import `in`.koreatech.business.domain.repository.OwnerMenuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Inject
class GetOwnerMenuUseCase(
    private val repository: OwnerMenuRepository
) {
    operator fun invoke(menuId: Int): Flow<Result<OwnerMenuDetail>> = flow {
        emit(repository.getOwnerMenu(menuId))
    }
}
