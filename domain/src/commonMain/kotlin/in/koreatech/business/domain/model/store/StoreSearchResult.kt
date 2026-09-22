package `in`.koreatech.business.domain.model.store

data class StoreSearchResult(
    val id: Int,
    val name: String,
    val phone: String,
    val isDeliveryAvailable: Boolean,
    val isCardAvailable: Boolean,
    val isBankTransferAvailable: Boolean
)
