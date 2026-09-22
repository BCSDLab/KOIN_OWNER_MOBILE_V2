package `in`.koreatech.business.data.request.store

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerMenuRequest(
    @SerialName("category_ids") val categoryIds: List<Int>,
    val description: String,
    @SerialName("image_urls") val imageUrls: List<String>,
    @SerialName("is_single") val isSingle: Boolean,
    val name: String,
    @SerialName("option_prices") val optionPrices: List<OwnerMenuPriceRequest>?,
    @SerialName("single_price") val singlePrice: Int?
)

@Serializable
data class OwnerMenuPriceRequest(
    val option: String,
    val price: Int
)
