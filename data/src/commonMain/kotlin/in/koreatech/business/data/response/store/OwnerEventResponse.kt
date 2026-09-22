package `in`.koreatech.business.data.response.store

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerEventsResponse(
    val events: List<OwnerEventResponse> = emptyList()
)

@Serializable
data class OwnerEventResponse(
    @SerialName("event_id") val eventId: Int,
    @SerialName("shop_id") val shopId: Int,
    @SerialName("shop_name") val shopName: String,
    val title: String,
    val content: String,
    @SerialName("thumbnail_images") val thumbnailImages: List<String> = emptyList(),
    @SerialName("start_date") val startDate: String,
    @SerialName("end_date") val endDate: String
)
