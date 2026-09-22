package `in`.koreatech.business.data.mapper

import `in`.koreatech.business.data.response.store.StoreSearchItemResponse
import `in`.koreatech.business.domain.model.store.StoreSearchResult

fun StoreSearchItemResponse.toStoreSearchResult(): StoreSearchResult =
    StoreSearchResult(
        id = id,
        name = name,
        phone = phone,
        isDeliveryAvailable = delivery,
        isCardAvailable = payCard,
        isBankTransferAvailable = payBank
    )
