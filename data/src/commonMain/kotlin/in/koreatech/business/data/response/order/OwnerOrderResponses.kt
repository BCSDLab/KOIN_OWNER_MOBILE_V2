package `in`.koreatech.business.data.response.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerOrderableShopsResponse(
    @SerialName("shops") val shops: List<OwnerOrderableShopResponse>
)

@Serializable
data class OwnerOrderableShopResponse(
    @SerialName("orderable_shop_id") val orderableShopId: Int,
    @SerialName("shop_id") val shopId: Int,
    @SerialName("name") val name: String,
    @SerialName("address") val address: String? = null,
    @SerialName("is_open") val isOpen: Boolean
)

@Serializable
data class OwnerOrdersResponse(
    @SerialName("orders") val orders: List<OwnerOrderResponse>
)

@Serializable
data class OwnerOrderResponse(
    @SerialName("id") val id: Int,
    @SerialName("order_number") val orderNumber: String,
    @SerialName("order_type") val orderType: String,
    @SerialName("order_status") val orderStatus: String,
    @SerialName("ordered_at") val orderedAt: String,
    @SerialName("estimated_at") val estimatedAt: String? = null,
    @SerialName("total_price") val totalPrice: Int
)

@Serializable
data class OwnerOrderDetailResponse(
    @SerialName("id") val id: Int,
    @SerialName("order_number") val orderNumber: String,
    @SerialName("order_type") val orderType: String,
    @SerialName("order_status") val orderStatus: String,
    @SerialName("ordered_at") val orderedAt: String,
    @SerialName("order_menus") val orderMenus: List<OwnerOrderMenuResponse>,
    @SerialName("receiver") val receiver: OwnerOrderReceiverResponse,
    @SerialName("payment") val payment: OwnerOrderPaymentResponse,
    @SerialName("completed_at") val completedAt: String? = null,
    @SerialName("canceled_at") val canceledAt: String? = null,
    @SerialName("canceled_reason") val canceledReason: String? = null
)

@Serializable
data class OwnerOrderMenuResponse(
    @SerialName("id") val id: Int,
    @SerialName("menu_name") val menuName: String,
    @SerialName("menu_price_name") val menuPriceName: String? = null,
    @SerialName("menu_price") val menuPrice: Int,
    @SerialName("quantity") val quantity: Int,
    @SerialName("options") val options: List<OwnerOrderMenuOptionResponse>
)

@Serializable
data class OwnerOrderMenuOptionResponse(
    @SerialName("option_group_name") val optionGroupName: String,
    @SerialName("option_name") val optionName: String,
    @SerialName("option_price") val optionPrice: Int,
    @SerialName("quantity") val quantity: Int
)

@Serializable
data class OwnerOrderReceiverResponse(
    @SerialName("name") val name: String,
    @SerialName("phone_number") val phoneNumber: String,
    @SerialName("address") val address: String? = null,
    @SerialName("address_detail") val addressDetail: String? = null,
    @SerialName("to_owner") val toOwner: String? = null,
    @SerialName("to_rider") val toRider: String? = null,
    @SerialName("provide_cutlery") val provideCutlery: Boolean
)

@Serializable
data class OwnerOrderPaymentResponse(
    @SerialName("method") val method: String,
    @SerialName("approved_at") val approvedAt: String,
    @SerialName("total_product_price") val totalProductPrice: Int,
    @SerialName("delivery_tip") val deliveryTip: Int,
    @SerialName("discount_amount") val discountAmount: Int,
    @SerialName("total_price") val totalPrice: Int
)
