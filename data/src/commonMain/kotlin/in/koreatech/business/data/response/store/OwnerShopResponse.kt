package `in`.koreatech.business.data.response.store

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerShopsResponse(
    val count: Int,
    val shops: List<OwnerShopSummaryResponse> = emptyList()
)

@Serializable
data class OwnerShopSummaryResponse(
    val id: Int,
    val name: String,
    @SerialName("is_event") val isEvent: Boolean
)

@Serializable
data class OwnerShopDetailResponse(
    val id: Int,
    val name: String? = null,
    val address: String? = null,
    val phone: String? = null,
    val description: String? = null,
    @SerialName("image_urls") val imageUrls: List<String> = emptyList(),
    @SerialName("shop_categories") val shopCategories: List<ShopCategoryResponse> = emptyList(),
    val open: List<ShopOpenResponse> = emptyList(),
    val delivery: Boolean = false,
    @SerialName("delivery_price") val deliveryPrice: Int = 0,
    @SerialName("pay_card") val payCard: Boolean = false,
    @SerialName("pay_bank") val payBank: Boolean = false,
    @SerialName("is_event") val isEvent: Boolean = false,
    val bank: String? = null,
    @SerialName("account_number") val accountNumber: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null
)

@Serializable
data class ShopCategoryResponse(
    val id: Int,
    val name: String
)

@Serializable
data class ShopOpenResponse(
    @SerialName("day_of_week") val dayOfWeek: String,
    val closed: Boolean,
    @SerialName("open_time") val openTime: String? = null,
    @SerialName("close_time") val closeTime: String? = null
)
