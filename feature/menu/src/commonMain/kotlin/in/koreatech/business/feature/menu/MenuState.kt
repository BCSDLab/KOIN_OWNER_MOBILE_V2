package `in`.koreatech.business.feature.menu

import `in`.koreatech.business.domain.model.store.OwnerMenuCategory
import `in`.koreatech.business.domain.model.store.OwnerShop
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MenuState(
    val shop: OwnerShop? = null,
    val categories: ImmutableList<OwnerMenuCategory> = persistentListOf(),
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
