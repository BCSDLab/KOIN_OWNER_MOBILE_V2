package `in`.koreatech.business.data.response.upload

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploadUrlResponse(
    @SerialName("file_url") val fileUrl: String,
    @SerialName("pre_signed_url") val preSignedUrl: String
)
