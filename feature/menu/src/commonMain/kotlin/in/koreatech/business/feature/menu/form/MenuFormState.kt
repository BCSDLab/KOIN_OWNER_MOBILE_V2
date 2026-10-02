package `in`.koreatech.business.feature.menu.form

import androidx.compose.foundation.text.input.TextFieldState
import `in`.koreatech.business.feature.menu.form.model.MenuFormCategory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf

data class MenuFormState(
    val shopId: Int,
    val menuId: Int?,
    val nameTextFieldState: TextFieldState = TextFieldState(),
    val descriptionTextFieldState: TextFieldState = TextFieldState(),
    val singlePriceTextFieldState: TextFieldState = TextFieldState(),
    val categories: ImmutableList<MenuFormCategory> = persistentListOf(),
    val selectedCategoryIds: ImmutableSet<Int> = persistentSetOf(),
    val isSinglePrice: Boolean = true,
    val optionPrices: ImmutableList<EditableMenuPrice> = persistentListOf(EditableMenuPrice()),
    val imageUrls: ImmutableList<String> = persistentListOf(),
    val pendingImageCount: Int = 0,
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val error: MenuFormError? = null
) {
    val name: String get() = nameTextFieldState.text.toString()
    val description: String get() = descriptionTextFieldState.text.toString()
    val singlePrice: String get() = singlePriceTextFieldState.text.toString()

    val isEdit: Boolean get() = menuId != null
    val isUploading: Boolean get() = pendingImageCount > 0
}
