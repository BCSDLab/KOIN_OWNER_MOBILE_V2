package `in`.koreatech.business.data.mapper

import `in`.koreatech.business.data.response.address.AddressResponse
import `in`.koreatech.business.domain.model.address.AddressSearchResult

fun AddressResponse.toAddressSearchResult(): AddressSearchResult = AddressSearchResult(
    buildingName = buildingName,
    roadAddress = roadAddress,
    jibunAddress = jibunAddress,
    zipCode = zipCode
)
