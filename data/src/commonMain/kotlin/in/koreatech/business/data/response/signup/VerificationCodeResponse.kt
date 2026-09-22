package `in`.koreatech.business.data.response.signup

import kotlinx.serialization.Serializable

@Serializable
data class VerificationCodeResponse(
    val token: String
)
