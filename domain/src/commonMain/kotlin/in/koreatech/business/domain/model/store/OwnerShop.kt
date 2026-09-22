package `in`.koreatech.business.domain.model.store

data class OwnerShop(
    val id: Int,
    val name: String,
    val hasActiveEvent: Boolean,
    val address: String?,
    val phone: String?,
    val description: String?,
    val imageUrls: List<String>,
    val categories: List<String>,
    val categoryIds: List<Int>,
    val operatingTimes: List<OperatingTime>,
    val isDeliveryAvailable: Boolean,
    val deliveryPrice: Int,
    val isCardAvailable: Boolean,
    val isBankTransferAvailable: Boolean,
    val bank: String?,
    val accountNumber: String?,
    val updatedAt: String?
)

data class OperatingTime(
    val dayOfWeek: String,
    val isClosed: Boolean,
    val openTime: String?,
    val closeTime: String?
)
