package `in`.koreatech.business.feature.order

import `in`.koreatech.business.feature.order.model.OrderServiceMenuUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf

enum class OrderServiceField {
    MINIMUM_ORDER_AMOUNT,
    CAMPUS_DELIVERY_TIP,
    OFF_CAMPUS_DELIVERY_TIP,
    BANK,
    ACCOUNT_NUMBER,
    ACCOUNT_HOLDER
}

enum class OrderServiceDocument {
    BUSINESS_LICENSE,
    BUSINESS_CERTIFICATE,
    BANK_COPY
}

data class OrderServiceState(
    val isLoadingMenus: Boolean = false,
    val hasMenuError: Boolean = false,
    val menus: ImmutableList<OrderServiceMenuUiModel> = persistentListOf(),
    val selectedMenuIds: ImmutableSet<Int> = persistentSetOf(),
    val minimumOrderAmount: String = "",
    val campusDeliveryTip: String = "",
    val offCampusDeliveryTip: String = "",
    val deliveryOption: String = "BOTH",
    val isTakeout: Boolean = true,
    val bank: String = "",
    val accountNumber: String = "",
    val accountHolder: String = "",
    val businessLicenseUrl: String? = null,
    val businessCertificateUrl: String? = null,
    val bankCopyUrl: String? = null,
    val uploadingDocuments: ImmutableSet<OrderServiceDocument> = persistentSetOf(),
    val isSubmitting: Boolean = false,
    val isSubmitted: Boolean = false,
    val hasUploadError: Boolean = false,
    val hasSubmitError: Boolean = false,
    val submitErrorMessage: String? = null
) {
    val canSubmit: Boolean
        get() = !isSubmitting && !isSubmitted && !isLoadingMenus && !hasMenuError && uploadingDocuments.isEmpty() &&
            minimumOrderAmount.toIntOrNull()?.let { it >= 0 } == true &&
            campusDeliveryTip.toIntOrNull()?.let { it >= 0 } == true &&
            offCampusDeliveryTip.toIntOrNull()?.let { it >= 0 } == true &&
            bank.isNotBlank() && accountNumber.isNotBlank() && accountHolder.isNotBlank() &&
            businessLicenseUrl != null && businessCertificateUrl != null && bankCopyUrl != null && selectedMenuIds.isNotEmpty()
}
