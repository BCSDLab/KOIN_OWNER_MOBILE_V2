package `in`.koreatech.business.data.request.signup

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerRegisterRequest(
    @SerialName("attachment_urls") val attachmentUrls: List<AttachmentUrlRequest>,
    @SerialName("company_number") val companyNumber: String,
    val name: String,
    val password: String,
    @SerialName("phone_number") val phoneNumber: String,
    @SerialName("shop_number") val shopNumber: String,
    @SerialName("shop_id") val shopId: Int? = null,
    @SerialName("shop_name") val shopName: String
)
