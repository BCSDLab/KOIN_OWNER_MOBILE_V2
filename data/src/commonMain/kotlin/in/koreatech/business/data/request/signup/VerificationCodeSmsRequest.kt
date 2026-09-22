package `in`.koreatech.business.data.request.signup

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerificationCodeSmsRequest(
    @SerialName("phone_number") val phoneNumber: String,
    @SerialName("certification_code") val certificationCode: String
)
