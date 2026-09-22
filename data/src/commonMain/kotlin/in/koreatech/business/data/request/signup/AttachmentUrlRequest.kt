package `in`.koreatech.business.data.request.signup

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttachmentUrlRequest(
    @SerialName("file_url") val fileUrl: String
)
