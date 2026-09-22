package `in`.koreatech.business.data.request.store

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerEventRequest(
    val title: String,
    val content: String,
    @SerialName("thumbnail_images") val thumbnailImages: List<String>,
    @SerialName("start_date") val startDate: String,
    @SerialName("end_date") val endDate: String
)
