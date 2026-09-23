package `in`.koreatech.business.data.mapper

import `in`.koreatech.business.data.response.store.OwnerShopDetailResponse
import `in`.koreatech.business.data.response.store.OwnerShopSummaryResponse
import `in`.koreatech.business.domain.model.store.OperatingTime
import `in`.koreatech.business.domain.model.store.OwnerShop
internal fun OwnerShopDetailResponse.toOwnerShop(): OwnerShop = toOwnerShop(
    shopName = requireNotNull(name),
    hasActiveEvent = isEvent
)

internal fun OwnerShopDetailResponse.toOwnerShop(summary: OwnerShopSummaryResponse): OwnerShop = toOwnerShop(
    shopName = name ?: summary.name,
    hasActiveEvent = isEvent || summary.isEvent
)

private fun OwnerShopDetailResponse.toOwnerShop(
    shopName: String,
    hasActiveEvent: Boolean
): OwnerShop = OwnerShop(
    id = id,
    name = shopName,
    hasActiveEvent = hasActiveEvent,
    address = address,
    phone = phone,
    description = description,
    imageUrls = imageUrls,
    categories = shopCategories.map { it.name },
    categoryIds = shopCategories.map { it.id },
    operatingTimes = open
        .map {
            OperatingTime(
                dayOfWeek = it.dayOfWeek,
                isClosed = it.closed,
                openTime = it.openTime,
                closeTime = it.closeTime
            )
        },
    isDeliveryAvailable = delivery,
    deliveryPrice = deliveryPrice,
    isCardAvailable = payCard,
    isBankTransferAvailable = payBank,
    bank = bank,
    accountNumber = accountNumber,
    updatedAt = updatedAt
)
