package `in`.koreatech.business.domain.usecase.store

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.model.store.OwnerEventForm
import `in`.koreatech.business.domain.repository.OwnerEventRepository

@Inject
class UpdateOwnerEventUseCase(
    private val repository: OwnerEventRepository
) {
    suspend operator fun invoke(
        shopId: Int,
        eventId: Int,
        event: OwnerEventForm
    ): Result<Unit> = repository.updateOwnerEvent(shopId, eventId, event)
}
