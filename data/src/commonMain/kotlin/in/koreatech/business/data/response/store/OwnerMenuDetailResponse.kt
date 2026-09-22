package `in`.koreatech.business.data.response.store

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerMenuDetailResponse(
    val id: Int,
    @SerialName("shop_id") val shopId: Int,
    val name: String,
    @SerialName("is_hidden") val isHidden: Boolean,
    @SerialName("is_single") val isSingle: Boolean,
    @SerialName("single_price") val singlePrice: Int? = null,
    @SerialName("option_prices") val optionPrices: List<OwnerMenuPriceResponse>? = null,
    val description: String,
    @SerialName("category_ids") val categoryIds: List<Int> = emptyList(),
    @SerialName("image_urls") val imageUrls: List<String> = emptyList()
)
