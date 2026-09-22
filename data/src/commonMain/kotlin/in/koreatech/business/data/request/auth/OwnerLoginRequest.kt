package `in`.koreatech.business.data.request.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerLoginRequest(
    @SerialName("account") val phoneNumber: String,
    val password: String
)
