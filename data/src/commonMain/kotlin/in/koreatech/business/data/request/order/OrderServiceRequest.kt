package `in`.koreatech.business.data.request.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderServiceRequest(
    @SerialName("minimum_order_amount") val minimumOrderAmount: Int,
    @SerialName("is_takeout") val isTakeout: Boolean,
    @SerialName("delivery_option") val deliveryOption: String,
    @SerialName("campus_delivery_tip") val campusDeliveryTip: Int,
    @SerialName("off_campus_delivery_tip") val offCampusDeliveryTip: Int,
    @SerialName("business_license_url") val businessLicenseUrl: String,
    @SerialName("business_certificate_url") val businessCertificateUrl: String,
    @SerialName("bank_copy_url") val bankCopyUrl: String,
    @SerialName("bank") val bank: String,
    @SerialName("account_number") val accountNumber: String,
    @SerialName("account_holder") val accountHolder: String,
    @SerialName("menus") val menus: List<OrderServiceMenuRequest>
)

@Serializable
data class OrderServiceMenuRequest(
    @SerialName("menu_id") val menuId: Int
)
