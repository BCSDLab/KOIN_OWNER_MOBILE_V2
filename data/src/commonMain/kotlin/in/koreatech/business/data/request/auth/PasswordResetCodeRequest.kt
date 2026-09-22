package `in`.koreatech.business.data.request.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PasswordResetCodeRequest(
    @SerialName("phone_number") val phoneNumber: String,
    @SerialName("certification_code") val code: String
)
