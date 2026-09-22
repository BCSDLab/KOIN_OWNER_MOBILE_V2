package `in`.koreatech.business.data.repository

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.data.mapper.toOwnerEvent
import `in`.koreatech.business.data.mapper.toOwnerEventRequest
import `in`.koreatech.business.data.source.remote.OwnerEventRemoteDataSource
import `in`.koreatech.business.data.util.suspendRunCatching
import `in`.koreatech.business.domain.model.store.OwnerEvent
import `in`.koreatech.business.domain.model.store.OwnerEventForm
import `in`.koreatech.business.domain.repository.OwnerEventRepository

@ContributesBinding(AppScope::class)
@Inject
@SingleIn(AppScope::class)
class OwnerEventRepositoryImpl(
    private val ownerEventRemoteDataSource: OwnerEventRemoteDataSource
) : OwnerEventRepository {
    override suspend fun getOwnerEvents(shopId: Int): Result<List<OwnerEvent>> =
        suspendRunCatching {
            ownerEventRemoteDataSource.getOwnerEvents(shopId).events.map { it.toOwnerEvent() }
        }

    override suspend fun createOwnerEvent(
        shopId: Int,
        event: OwnerEventForm
    ): Result<Unit> =
        suspendRunCatching {
            ownerEventRemoteDataSource.createOwnerEvent(shopId, event.toOwnerEventRequest())
        }

    override suspend fun updateOwnerEvent(
        shopId: Int,
        eventId: Int,
        event: OwnerEventForm
    ): Result<Unit> =
        suspendRunCatching {
            ownerEventRemoteDataSource.updateOwnerEvent(shopId, eventId, event.toOwnerEventRequest())
        }

    override suspend fun deleteOwnerEvent(
        shopId: Int,
        eventId: Int
    ): Result<Unit> =
        suspendRunCatching {
            ownerEventRemoteDataSource.deleteOwnerEvent(shopId, eventId)
        }
}
