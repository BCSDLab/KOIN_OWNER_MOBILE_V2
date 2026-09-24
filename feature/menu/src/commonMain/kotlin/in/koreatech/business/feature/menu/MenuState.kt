package `in`.koreatech.business.feature.menu

import `in`.koreatech.business.feature.menu.model.MenuCategoryUiModel
import `in`.koreatech.business.feature.menu.model.MenuShopUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MenuState(
    val shop: MenuShopUiModel? = null,
    val categories: ImmutableList<MenuCategoryUiModel> = persistentListOf(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isFabMenuExpanded: Boolean = false,
    val isCategoryEditorVisible: Boolean = false,
    val categoryEditorId: Int? = null,
    val categoryName: String = "",
    val isSavingCategory: Boolean = false,
    val deleteCategoryId: Int? = null,
    val deleteCategoryName: String? = null,
    val isDeletingCategory: Boolean = false,
    val isDeleting: Boolean = false,
    val deleteMenuId: Int? = null,
    val deleteMenuName: String? = null
)
