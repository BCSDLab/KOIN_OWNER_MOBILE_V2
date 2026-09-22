package `in`.koreatech.business.data.response.store

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShopCategoriesResponse(
    @SerialName("shop_categories") val categories: List<ShopCategoryItemResponse> = emptyList()
)

@Serializable
data class ShopCategoryItemResponse(
    val id: Int,
    val name: String,
    @SerialName("image_url") val imageUrl: String? = null
)
