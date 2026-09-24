package `in`.koreatech.business.feature.home.model

import `in`.koreatech.business.domain.model.store.OwnerShop
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class HomeShop(
    val id: Int,
    val name: String,
    val hasActiveEvent: Boolean,
    val address: String?,
    val phone: String?,
    val description: String?,
    val imageUrls: ImmutableList<String>,
    val categories: ImmutableList<String>,
    val operatingTimes: ImmutableList<HomeOperatingTime>,
    val isDeliveryAvailable: Boolean,
    val deliveryPrice: Int,
    val isCardAvailable: Boolean,
    val isBankTransferAvailable: Boolean,
    val bank: String?,
    val accountNumber: String?
)

data class HomeOperatingTime(
    val dayOfWeek: String,
    val isClosed: Boolean,
    val openTime: String?,
    val closeTime: String?
)

fun OwnerShop.toHomeShop() = HomeShop(
    id = id,
    name = name,
    hasActiveEvent = hasActiveEvent,
    address = address,
    phone = phone,
    description = description,
    imageUrls = imageUrls.toImmutableList(),
    categories = categories.toImmutableList(),
    operatingTimes = operatingTimes.map { operatingTime ->
        HomeOperatingTime(
            dayOfWeek = operatingTime.dayOfWeek,
            isClosed = operatingTime.isClosed,
            openTime = operatingTime.openTime,
            closeTime = operatingTime.closeTime
        )
    }.toImmutableList(),
    isDeliveryAvailable = isDeliveryAvailable,
    deliveryPrice = deliveryPrice,
    isCardAvailable = isCardAvailable,
    isBankTransferAvailable = isBankTransferAvailable,
    bank = bank,
    accountNumber = accountNumber
)
