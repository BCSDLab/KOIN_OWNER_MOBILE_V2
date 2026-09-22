package `in`.koreatech.business.data.response.store

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoreSearchResponse(
    val shops: List<StoreSearchItemResponse> = emptyList()
)

@Serializable
data class StoreSearchItemResponse(
    val id: Int = 0,
    val name: String = "",
    val phone: String = "",
    val delivery: Boolean = false,
    @SerialName("pay_card") val payCard: Boolean = false,
    @SerialName("pay_bank") val payBank: Boolean = false
)
