package `in`.koreatech.business.data.request.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PasswordResetRequest(
    @SerialName("phone_number") val phoneNumber: String,
    val password: String
)
