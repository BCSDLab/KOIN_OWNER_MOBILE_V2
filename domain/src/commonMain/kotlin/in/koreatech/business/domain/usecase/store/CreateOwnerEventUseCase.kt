package `in`.koreatech.business.domain.usecase.store

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.model.store.OwnerEventForm
import `in`.koreatech.business.domain.repository.OwnerEventRepository

@Inject
class CreateOwnerEventUseCase(
    private val repository: OwnerEventRepository
) {
    suspend operator fun invoke(
        shopId: Int,
        event: OwnerEventForm
    ): Result<Unit> = repository.createOwnerEvent(shopId, event)
}
