package `in`.koreatech.business.data.response.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerLoginResponse(
    @SerialName("token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String
)
