package `in`.koreatech.business.feature.menu.form

import `in`.koreatech.business.domain.model.store.OwnerMenuCategoryOption
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf

data class MenuFormState(
    val shopId: Int,
    val menuId: Int?,
    val categories: ImmutableList<OwnerMenuCategoryOption> = persistentListOf(),
    val selectedCategoryIds: ImmutableSet<Int> = persistentSetOf(),
    val name: String = "",
    val description: String = "",
    val isSinglePrice: Boolean = true,
    val singlePrice: String = "",
    val optionPrices: ImmutableList<EditableMenuPrice> = persistentListOf(EditableMenuPrice()),
    val imageUrls: ImmutableList<String> = persistentListOf(),
    val isLoading: Boolean = true,
    val isUploading: Boolean = false,
    val isSaving: Boolean = false,
    val error: MenuFormError? = null
) {
    val isEdit: Boolean get() = menuId != null
}
