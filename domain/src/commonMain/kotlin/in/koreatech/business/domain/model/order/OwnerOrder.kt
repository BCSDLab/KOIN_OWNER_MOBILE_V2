package `in`.koreatech.business.domain.model.order

data class OwnerOrderableShop(
    val id: Int,
    val shopId: Int,
    val name: String,
    val address: String?,
    val isOpen: Boolean
)

enum class OwnerOrderCategory {
    NEW,
    COOKING,
    DELIVERING,
    COMPLETED
}

data class OwnerOrder(
    val id: Int,
    val number: String,
    val type: String,
    val status: String,
    val orderedAt: String,
    val estimatedAt: String?,
    val totalPrice: Int
)

data class OwnerOrderDetail(
    val id: Int,
    val number: String,
    val type: String,
    val status: String,
    val orderedAt: String,
    val menus: List<OwnerOrderMenu>,
    val receiver: OwnerOrderReceiver,
    val payment: OwnerOrderPayment,
    val completedAt: String?,
    val canceledAt: String?,
    val canceledReason: String?
)

data class OwnerOrderMenu(
    val id: Int,
    val name: String,
    val priceName: String?,
    val price: Int,
    val quantity: Int,
    val options: List<OwnerOrderMenuOption>
)

data class OwnerOrderMenuOption(
    val groupName: String,
    val name: String,
    val price: Int,
    val quantity: Int
)

data class OwnerOrderReceiver(
    val name: String,
    val phoneNumber: String,
    val address: String?,
    val addressDetail: String?,
    val toOwner: String?,
    val toRider: String?,
    val provideCutlery: Boolean
)

data class OwnerOrderPayment(
    val method: String,
    val approvedAt: String,
    val totalProductPrice: Int,
    val deliveryTip: Int,
    val discountAmount: Int,
    val totalPrice: Int
)
