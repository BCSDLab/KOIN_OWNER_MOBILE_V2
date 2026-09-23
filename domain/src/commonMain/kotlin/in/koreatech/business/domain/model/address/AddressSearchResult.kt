package `in`.koreatech.business.domain.model.address

data class AddressSearchResult(
    val buildingName: String,
    val roadAddress: String,
    val jibunAddress: String,
    val zipCode: String
) {
    val displayAddress: String
        get() = roadAddress.ifBlank { jibunAddress }
}
