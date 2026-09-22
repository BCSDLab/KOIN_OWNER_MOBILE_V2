package `in`.koreatech.business.data.request.signup

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerificationSmsRequest(
    @SerialName("phone_number") val phoneNumber: String
)
