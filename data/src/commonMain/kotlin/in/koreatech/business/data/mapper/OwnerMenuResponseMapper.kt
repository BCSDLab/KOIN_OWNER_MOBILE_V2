package `in`.koreatech.business.data.mapper

import `in`.koreatech.business.data.response.store.OwnerMenuCategoriesResponse
import `in`.koreatech.business.data.response.store.OwnerMenuCategoryResponse
import `in`.koreatech.business.data.response.store.OwnerMenuDetailResponse
import `in`.koreatech.business.data.response.store.OwnerMenuItemResponse
import `in`.koreatech.business.data.response.store.OwnerMenuPriceResponse
import `in`.koreatech.business.domain.model.store.OwnerMenu
import `in`.koreatech.business.domain.model.store.OwnerMenuCategory
import `in`.koreatech.business.domain.model.store.OwnerMenuCategoryOption
import `in`.koreatech.business.domain.model.store.OwnerMenuDetail
import `in`.koreatech.business.domain.model.store.OwnerMenuPrice
internal fun OwnerMenuCategoryResponse.toOwnerMenuCategory(): OwnerMenuCategory = OwnerMenuCategory(
    id = id,
    name = name,
    menus = menus.map(OwnerMenuItemResponse::toOwnerMenu)
)

internal fun OwnerMenuItemResponse.toOwnerMenu(): OwnerMenu = OwnerMenu(
    id = id,
    name = name.orEmpty(),
    isHidden = isHidden,
    description = description,
    prices = toOwnerMenuPrices(isSingle, singlePrice, optionPrices),
    imageUrls = imageUrls.orEmpty()
)

internal fun OwnerMenuDetailResponse.toOwnerMenuDetail(): OwnerMenuDetail = OwnerMenuDetail(
    id = id,
    shopId = shopId,
    name = name,
    isHidden = isHidden,
    categoryIds = categoryIds,
    description = description,
    prices = toOwnerMenuPrices(isSingle, singlePrice, optionPrices),
    imageUrls = imageUrls
)

internal fun OwnerMenuCategoriesResponse.toOwnerMenuCategoryOptions(): List<OwnerMenuCategoryOption> = menuCategories.map { OwnerMenuCategoryOption(id = it.id, name = it.name) }

private fun toOwnerMenuPrices(
    isSingle: Boolean,
    singlePrice: Int?,
    optionPrices: List<OwnerMenuPriceResponse>?
): List<OwnerMenuPrice> = if (isSingle) {
    listOfNotNull(singlePrice?.let { OwnerMenuPrice(option = null, price = it) })
} else {
    optionPrices.orEmpty().map { OwnerMenuPrice(option = it.option, price = it.price) }
}
