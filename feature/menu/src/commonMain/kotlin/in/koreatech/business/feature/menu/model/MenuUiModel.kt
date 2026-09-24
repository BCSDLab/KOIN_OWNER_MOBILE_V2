package `in`.koreatech.business.feature.menu.model

import `in`.koreatech.business.domain.model.store.OwnerMenu
import `in`.koreatech.business.domain.model.store.OwnerMenuCategory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class MenuShopUiModel(
    val id: Int,
    val name: String
)

data class MenuCategoryUiModel(
    val id: Int,
    val name: String,
    val menus: ImmutableList<MenuUiModel>
)

data class MenuUiModel(
    val id: Int,
    val name: String,
    val isHidden: Boolean,
    val description: String?,
    val prices: ImmutableList<MenuPriceUiModel>,
    val imageUrls: ImmutableList<String>
)

data class MenuPriceUiModel(
    val option: String?,
    val price: Int
)

internal fun OwnerMenuCategory.toMenuCategoryUiModel() = MenuCategoryUiModel(
    id = id,
    name = name,
    menus = menus.map(OwnerMenu::toMenuUiModel).toImmutableList()
)

private fun OwnerMenu.toMenuUiModel() = MenuUiModel(
    id = id,
    name = name,
    isHidden = isHidden,
    description = description,
    prices = prices.map { price ->
        MenuPriceUiModel(
            option = price.option,
            price = price.price
        )
    }.toImmutableList(),
    imageUrls = imageUrls.toImmutableList()
)
