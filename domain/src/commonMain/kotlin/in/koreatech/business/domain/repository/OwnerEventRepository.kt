package `in`.koreatech.business.domain.repository

import `in`.koreatech.business.domain.model.store.OwnerEvent
import `in`.koreatech.business.domain.model.store.OwnerEventForm

interface OwnerEventRepository {
    suspend fun getOwnerEvents(shopId: Int): Result<List<OwnerEvent>>

    suspend fun createOwnerEvent(
        shopId: Int,
        event: OwnerEventForm
    ): Result<Unit>

    suspend fun updateOwnerEvent(
        shopId: Int,
        eventId: Int,
        event: OwnerEventForm
    ): Result<Unit>

    suspend fun deleteOwnerEvent(
        shopId: Int,
        eventId: Int
    ): Result<Unit>
}
