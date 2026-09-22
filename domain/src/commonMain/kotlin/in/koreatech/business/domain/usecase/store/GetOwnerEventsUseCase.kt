package `in`.koreatech.business.domain.usecase.store

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.model.store.OwnerEvent
import `in`.koreatech.business.domain.repository.OwnerEventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Inject
class GetOwnerEventsUseCase(
    private val repository: OwnerEventRepository
) {
    operator fun invoke(shopId: Int): Flow<Result<List<OwnerEvent>>> =
        flow {
            emit(repository.getOwnerEvents(shopId))
        }
}
