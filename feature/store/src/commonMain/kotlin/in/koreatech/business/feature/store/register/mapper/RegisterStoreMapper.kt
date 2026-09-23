package `in`.koreatech.business.feature.store.register.mapper

import `in`.koreatech.business.domain.model.store.OperatingTime
import `in`.koreatech.business.domain.model.store.OwnerShop
import `in`.koreatech.business.domain.model.store.OwnerShopForm
import `in`.koreatech.business.domain.model.store.ShopCategory
import `in`.koreatech.business.feature.store.register.RegisterStoreDay
import `in`.koreatech.business.feature.store.register.RegisterStoreOperatingTime
import `in`.koreatech.business.feature.store.register.RegisterStoreState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet

internal fun OwnerShop.toRegisterStoreState(categories: List<ShopCategory>): RegisterStoreState = RegisterStoreState(
    shopId = id,
    categories = categories.toImmutableList(),
    selectedCategoryId = categoryIds.firstOrNull(),
    storeName = name,
    address = address.orEmpty(),
    phoneNumber = phone.orEmpty().filter(Char::isDigit),
    deliveryFee = deliveryPrice.toString(),
    operatingTimes = toRegisterStoreOperatingTimes(),
    otherInfo = description.orEmpty(),
    isDeliveryAvailable = isDeliveryAvailable,
    isCardAvailable = isCardAvailable,
    isBankTransferAvailable = isBankTransferAvailable,
    imageUrls = imageUrls.toImmutableList()
)

internal fun RegisterStoreState.toOwnerShopForm(categoryId: Int): OwnerShopForm = OwnerShopForm(
    mainCategoryId = categoryId,
    categoryIds = listOf(categoryId).toImmutableList(),
    name = storeName.trim(),
    address = address.trim(),
    phone = phoneNumber,
    description = otherInfo.trim(),
    imageUrls = imageUrls,
    operatingTimes = toOperatingTimes(),
    isDeliveryAvailable = isDeliveryAvailable,
    deliveryPrice = deliveryFee.toIntOrNull() ?: 0,
    isCardAvailable = isCardAvailable,
    isBankTransferAvailable = isBankTransferAvailable
)

private fun OwnerShop.toRegisterStoreOperatingTimes() = operatingTimes
    .filterNot(OperatingTime::isClosed)
    .mapNotNull { operatingTime -> operatingTime.toRegisterStoreOperatingTime() }
    .groupBy { operatingTime ->
        OperatingTimeKey(
            openingTime = operatingTime.openingTime,
            closingTime = operatingTime.closingTime,
            is24Hours = operatingTime.is24Hours
        )
    }.map { (key, operatingTimes) ->
        RegisterStoreOperatingTime(
            days = operatingTimes.map { it.days.single() }.toImmutableSet(),
            openingTime = key.openingTime,
            closingTime = key.closingTime,
            is24Hours = key.is24Hours
        )
    }.toImmutableList()

private fun OperatingTime.toRegisterStoreOperatingTime(): RegisterStoreOperatingTime? {
    val day = RegisterStoreDay.entries.firstOrNull {
        it.name.equals(dayOfWeek, ignoreCase = true)
    } ?: return null
    return RegisterStoreOperatingTime(
        days = setOf(day).toImmutableSet(),
        openingTime = openTime ?: DEFAULT_OPENING_TIME,
        closingTime = closeTime ?: DEFAULT_CLOSING_TIME,
        is24Hours = isOpenAllDay
    )
}

private fun RegisterStoreState.toOperatingTimes() = RegisterStoreDay.entries
    .map { day ->
        val registerStoreOperatingTime = operatingTimes.firstOrNull { day in it.days }
        val isOpen = registerStoreOperatingTime != null
        OperatingTime(
            dayOfWeek = day.name.uppercase(),
            isClosed = !isOpen,
            openTime = registerStoreOperatingTime?.resolvedOpeningTime,
            closeTime = registerStoreOperatingTime?.resolvedClosingTime
        )
    }.toImmutableList()

private val OperatingTime.isOpenAllDay: Boolean
    get() = openTime == OPEN_ALL_DAY && closeTime == CLOSE_ALL_DAY

private val RegisterStoreOperatingTime.resolvedOpeningTime: String
    get() = if (is24Hours) OPEN_ALL_DAY else openingTime

private val RegisterStoreOperatingTime.resolvedClosingTime: String
    get() = if (is24Hours) CLOSE_ALL_DAY else closingTime

private data class OperatingTimeKey(
    val openingTime: String,
    val closingTime: String,
    val is24Hours: Boolean
)

private const val OPEN_ALL_DAY = "00:00"
private const val CLOSE_ALL_DAY = "23:59"
private const val DEFAULT_OPENING_TIME = "09:00"
private const val DEFAULT_CLOSING_TIME = "22:00"
