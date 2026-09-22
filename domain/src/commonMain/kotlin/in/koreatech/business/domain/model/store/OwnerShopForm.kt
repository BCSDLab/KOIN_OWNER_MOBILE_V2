package `in`.koreatech.business.domain.model.store

data class OwnerShopForm(
    val mainCategoryId: Int,
    val categoryIds: List<Int>,
    val name: String,
    val address: String,
    val phone: String,
    val description: String,
    val imageUrls: List<String>,
    val operatingTimes: List<OperatingTime>,
    val isDeliveryAvailable: Boolean,
    val deliveryPrice: Int,
    val isCardAvailable: Boolean,
    val isBankTransferAvailable: Boolean
)
