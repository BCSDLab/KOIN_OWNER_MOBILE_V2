package `in`.koreatech.business.data.response.store

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerMenuResponse(
    val count: Int,
    @SerialName("menu_categories") val menuCategories: List<OwnerMenuCategoryResponse> = emptyList(),
    @SerialName("updated_at") val updatedAt: String
)

@Serializable
data class OwnerMenuCategoryResponse(
    val id: Int,
    val name: String,
    val menus: List<OwnerMenuItemResponse> = emptyList()
)

@Serializable
data class OwnerMenuItemResponse(
    val id: Int,
    val name: String? = null,
    @SerialName("is_hidden") val isHidden: Boolean,
    @SerialName("is_single") val isSingle: Boolean,
    @SerialName("single_price") val singlePrice: Int? = null,
    @SerialName("option_prices") val optionPrices: List<OwnerMenuPriceResponse>? = null,
    val description: String? = null,
    @SerialName("image_urls") val imageUrls: List<String>? = null
)

@Serializable
data class OwnerMenuPriceResponse(
    val option: String,
    val price: Int
)
