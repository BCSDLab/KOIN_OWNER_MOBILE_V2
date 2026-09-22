package `in`.koreatech.business.data.source.remote

import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.data.request.store.OwnerEventRequest
import `in`.koreatech.business.data.response.store.OwnerEventsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody

@Inject
@SingleIn(AppScope::class)
class OwnerEventRemoteDataSource(
    private val httpClient: HttpClient
) {
    suspend fun getOwnerEvents(shopId: Int): OwnerEventsResponse = httpClient.get("owner/shops/$shopId/event").body()

    suspend fun createOwnerEvent(
        shopId: Int,
        request: OwnerEventRequest
    ) {
        httpClient.post("owner/shops/$shopId/event") { setBody(request) }
    }

    suspend fun updateOwnerEvent(
        shopId: Int,
        eventId: Int,
        request: OwnerEventRequest
    ) {
        httpClient.put("owner/shops/$shopId/events/$eventId") { setBody(request) }
    }

    suspend fun deleteOwnerEvent(
        shopId: Int,
        eventId: Int
    ) {
        httpClient.delete("owner/shops/$shopId/events/$eventId")
    }
}
