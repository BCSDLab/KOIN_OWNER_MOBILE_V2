package `in`.koreatech.business.data.mapper

import `in`.koreatech.business.data.request.store.OwnerShopOpenRequest
import `in`.koreatech.business.data.request.store.OwnerShopRequest
import `in`.koreatech.business.domain.model.store.OwnerShopForm

internal fun OwnerShopForm.toOwnerShopRequest(): OwnerShopRequest =
    OwnerShopRequest(
        address = address,
        mainCategoryId = mainCategoryId,
        categoryIds = categoryIds,
        delivery = isDeliveryAvailable,
        deliveryPrice = deliveryPrice,
        description = description,
        imageUrls = imageUrls,
        name = name,
        open =
        operatingTimes.map {
            OwnerShopOpenRequest(
                dayOfWeek = it.dayOfWeek,
                closed = it.isClosed,
                openTime = it.openTime,
                closeTime = it.closeTime
            )
        },
        payBank = isBankTransferAvailable,
        payCard = isCardAvailable,
        phone = phone.toPhoneNumber()
    )

private fun String.toPhoneNumber(): String =
    when (length) {
        10 -> "${take(3)}-${substring(3, 6)}-${takeLast(4)}"
        11 -> "${take(3)}-${substring(3, 7)}-${takeLast(4)}"
        else -> this
    }
