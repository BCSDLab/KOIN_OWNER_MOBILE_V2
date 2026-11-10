package `in`.koreatech.business.domain.model.order

data class OrderServiceApplication(
    val minimumOrderAmount: Int,
    val isTakeout: Boolean,
    val deliveryOption: String,
    val campusDeliveryTip: Int,
    val offCampusDeliveryTip: Int,
    val businessLicenseUrl: String,
    val businessCertificateUrl: String,
    val bankCopyUrl: String,
    val bank: String,
    val accountNumber: String,
    val accountHolder: String,
    val menuIds: List<Int>
)
