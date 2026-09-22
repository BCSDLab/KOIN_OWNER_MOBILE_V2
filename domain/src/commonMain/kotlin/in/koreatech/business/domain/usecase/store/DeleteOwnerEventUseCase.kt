package `in`.koreatech.business.domain.usecase.store

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.repository.OwnerEventRepository

@Inject
class DeleteOwnerEventUseCase(
    private val repository: OwnerEventRepository
) {
    suspend operator fun invoke(
        shopId: Int,
        eventId: Int
    ): Result<Unit> = repository.deleteOwnerEvent(shopId, eventId)
}
