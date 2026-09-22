package `in`.koreatech.business.data.request.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PasswordResetPhoneRequest(
    @SerialName("phone_number") val phoneNumber: String
)
