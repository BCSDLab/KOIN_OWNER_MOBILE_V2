package `in`.koreatech.business.data.request.store

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerShopRequest(
    val address: String,
    @SerialName("main_category_id") val mainCategoryId: Int,
    @SerialName("category_ids") val categoryIds: List<Int>,
    val delivery: Boolean,
    @SerialName("delivery_price") val deliveryPrice: Int,
    val description: String,
    @SerialName("image_urls") val imageUrls: List<String>,
    val name: String,
    val open: List<OwnerShopOpenRequest>,
    @SerialName("pay_bank") val payBank: Boolean,
    @SerialName("pay_card") val payCard: Boolean,
    val phone: String
)

@Serializable
data class OwnerShopOpenRequest(
    @SerialName("day_of_week") val dayOfWeek: String,
    val closed: Boolean,
    @SerialName("open_time") val openTime: String?,
    @SerialName("close_time") val closeTime: String?
)
