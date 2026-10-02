package `in`.koreatech.business.feature.menu

import androidx.compose.foundation.text.input.TextFieldState
import `in`.koreatech.business.feature.menu.model.MenuCategoryUiModel
import `in`.koreatech.business.feature.menu.model.MenuShopUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MenuState(
    val categoryNameTextFieldState: TextFieldState = TextFieldState(),
    val shop: MenuShopUiModel? = null,
    val categories: ImmutableList<MenuCategoryUiModel> = persistentListOf(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isFabMenuExpanded: Boolean = false,
    val isCategoryEditorVisible: Boolean = false,
    val categoryEditorId: Int? = null,
    val isSavingCategory: Boolean = false,
    val deleteCategoryId: Int? = null,
    val deleteCategoryName: String? = null,
    val isDeletingCategory: Boolean = false,
    val isDeleting: Boolean = false,
    val deleteMenuId: Int? = null,
    val deleteMenuName: String? = null
) {
    val categoryName: String get() = categoryNameTextFieldState.text.toString()
}
