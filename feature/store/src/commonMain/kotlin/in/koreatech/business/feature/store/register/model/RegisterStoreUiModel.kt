package `in`.koreatech.business.feature.store.register.model

import `in`.koreatech.business.domain.model.address.AddressSearchResult
import `in`.koreatech.business.domain.model.store.ShopCategory

data class RegisterStoreCategory(
    val id: Int,
    val name: String,
    val imageUrl: String?
)

data class RegisterStoreAddress(
    val buildingName: String,
    val roadAddress: String,
    val jibunAddress: String,
    val zipCode: String
) {
    val displayAddress: String
        get() = roadAddress.ifBlank { jibunAddress }
}

internal fun ShopCategory.toRegisterStoreCategory() = RegisterStoreCategory(
    id = id,
    name = name,
    imageUrl = imageUrl
)

internal fun AddressSearchResult.toRegisterStoreAddress() = RegisterStoreAddress(
    buildingName = buildingName,
    roadAddress = roadAddress,
    jibunAddress = jibunAddress,
    zipCode = zipCode
)
