package `in`.koreatech.business.feature.menu.form

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.zacsweers.metrox.viewmodel.assistedMetroViewModel
import `in`.koreatech.business.core.designsystem.component.KoinImagePreview
import `in`.koreatech.business.core.designsystem.component.KoinUnderlineTextField
import `in`.koreatech.business.core.designsystem.component.topbar.KoinTopAppBar
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_won
import `in`.koreatech.business.core.designsystem.generated.resources.error_image_upload
import `in`.koreatech.business.core.designsystem.generated.resources.error_menu_category_load
import `in`.koreatech.business.core.designsystem.generated.resources.error_menu_category_required
import `in`.koreatech.business.core.designsystem.generated.resources.error_menu_detail_load
import `in`.koreatech.business.core.designsystem.generated.resources.error_menu_name_required
import `in`.koreatech.business.core.designsystem.generated.resources.error_menu_price_invalid
import `in`.koreatech.business.core.designsystem.generated.resources.error_menu_save
import `in`.koreatech.business.core.designsystem.generated.resources.menu_add_title
import `in`.koreatech.business.core.designsystem.generated.resources.menu_description
import `in`.koreatech.business.core.designsystem.generated.resources.menu_description_hint
import `in`.koreatech.business.core.designsystem.generated.resources.menu_edit_title
import `in`.koreatech.business.core.designsystem.generated.resources.menu_image_add
import `in`.koreatech.business.core.designsystem.generated.resources.menu_image_count
import `in`.koreatech.business.core.designsystem.generated.resources.menu_image_description
import `in`.koreatech.business.core.designsystem.generated.resources.menu_image_uploading
import `in`.koreatech.business.core.designsystem.generated.resources.menu_name
import `in`.koreatech.business.core.designsystem.generated.resources.menu_name_hint
import `in`.koreatech.business.core.designsystem.generated.resources.menu_option_add
import `in`.koreatech.business.core.designsystem.generated.resources.menu_option_price
import `in`.koreatech.business.core.designsystem.generated.resources.menu_price
import `in`.koreatech.business.core.designsystem.generated.resources.menu_price_hint
import `in`.koreatech.business.core.designsystem.generated.resources.menu_save
import `in`.koreatech.business.core.designsystem.generated.resources.menu_saving
import `in`.koreatech.business.core.designsystem.generated.resources.menu_single_price
import `in`.koreatech.business.core.designsystem.noRippleClickable
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.core.file.rememberImageFilePicker
import `in`.koreatech.business.core.viewmodel.rememberSavedStateViewModelCreationExtras
import `in`.koreatech.business.feature.menu.component.MenuCategorySelector
import `in`.koreatech.business.feature.menu.component.MenuOptionPriceRow
import org.jetbrains.compose.resources.stringResource
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun MenuFormScreen(
    shopId: Int,
    menuId: Int?,
    onBack: () -> Unit,
    onSaved: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MenuFormViewModel = menuFormViewModel(shopId, menuId)
) {
    val state by viewModel.collectAsState()
    viewModel.collectSideEffect { if (it == MenuFormSideEffect.Saved) onSaved() }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            KoinTopAppBar(
                title = {
                    Text(
                        text =
                        stringResource(
                            if (state.isEdit) Res.string.menu_edit_title else Res.string.menu_add_title
                        ),
                        style = KoinTheme.typography.medium18
                    )
                },
                onNavigationIconClick = onBack
            )
        },
        containerColor = KoinTheme.colors.neutral0
    ) { paddingValues ->
        MenuFormScreenImpl(
            state = state,
            onNameChange = viewModel::updateName,
            onDescriptionChange = viewModel::updateDescription,
            onCategoryClick = viewModel::toggleCategory,
            onSinglePriceChange = viewModel::setSinglePrice,
            onPriceChange = viewModel::updateSinglePrice,
            onAddOption = viewModel::addOption,
            onDeleteOption = viewModel::deleteOption,
            onOptionChange = viewModel::updateOption,
            onUploadImage = viewModel::uploadImage,
            onImageSelectionFailed = viewModel::onImageSelectionFailed,
            onDeleteImage = viewModel::deleteImage,
            onSave = viewModel::save,
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        )
    }
}

@Composable
private fun menuFormViewModel(
    shopId: Int,
    menuId: Int?
): MenuFormViewModel {
    val extras =
        rememberSavedStateViewModelCreationExtras(shopId, menuId) {
            putInt(MenuFormViewModel.SHOP_ID_KEY, shopId)
            menuId?.let { putInt(MenuFormViewModel.MENU_ID_KEY, it) }
        }
    return assistedMetroViewModel(extras = extras)
}

@Composable
fun MenuFormScreenImpl(
    state: MenuFormState,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onCategoryClick: (Int) -> Unit,
    onSinglePriceChange: (Boolean) -> Unit,
    onPriceChange: (String) -> Unit,
    onAddOption: () -> Unit,
    onDeleteOption: (Int) -> Unit,
    onOptionChange: (Int, String?, String?) -> Unit,
    onUploadImage: (String, String, ByteArray) -> Unit,
    onImageSelectionFailed: () -> Unit,
    onDeleteImage: (Int) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    val openImagePicker =
        rememberImageFilePicker(
            onImagePicked = { onUploadImage(it.name, it.contentType, it.bytes) },
            onFailure = { onImageSelectionFailed() }
        )
    if (state.isLoading) {
        Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            CircularProgressIndicator(color = KoinTheme.colors.primary500)
        }
        return
    }
    Column(
        modifier = modifier.imePadding().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        KoinUnderlineTextField(
            title = stringResource(Res.string.menu_name),
            value = state.name,
            onValueChange = onNameChange,
            placeholder = stringResource(Res.string.menu_name_hint)
        )
        MenuCategorySelector(state.categories, state.selectedCategoryIds, onCategoryClick)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(Res.string.menu_price),
                style = KoinTheme.typography.medium15,
                color = KoinTheme.colors.neutral800
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PriceTypeButton(
                    text = stringResource(Res.string.menu_single_price),
                    selected = state.isSinglePrice,
                    onClick = { onSinglePriceChange(true) }
                )
                PriceTypeButton(
                    text = stringResource(Res.string.menu_option_price),
                    selected = !state.isSinglePrice,
                    onClick = { onSinglePriceChange(false) }
                )
            }
            if (state.isSinglePrice) {
                KoinUnderlineTextField(
                    title = "",
                    value = state.singlePrice,
                    onValueChange = onPriceChange,
                    placeholder = stringResource(Res.string.menu_price_hint),
                    keyboardType = KeyboardType.Number,
                    suffix = stringResource(Res.string.common_won)
                )
            } else {
                state.optionPrices.forEachIndexed { index, price ->
                    MenuOptionPriceRow(
                        index = index,
                        value = price,
                        onOptionChange = { onOptionChange(index, it, null) },
                        onPriceChange = { onOptionChange(index, null, it) },
                        onDelete = { onDeleteOption(index) }
                    )
                }
                Text(
                    stringResource(Res.string.menu_option_add),
                    style = KoinTheme.typography.medium14,
                    color = KoinTheme.colors.primary500,
                    modifier = Modifier.noRippleClickable(onClick = onAddOption).padding(vertical = 8.dp)
                )
            }
        }
        KoinUnderlineTextField(
            title = stringResource(Res.string.menu_description),
            value = state.description,
            onValueChange = onDescriptionChange,
            placeholder = stringResource(Res.string.menu_description_hint),
            singleLine = false
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(Res.string.menu_image_count, state.imageUrls.size),
                style = KoinTheme.typography.medium15
            )
            if (state.imageUrls.isNotEmpty()) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    state.imageUrls.forEachIndexed { index, imageUrl ->
                        KoinImagePreview(
                            imageUrl = imageUrl,
                            contentDescription =
                            stringResource(
                                Res.string.menu_image_description,
                                index + 1
                            ),
                            onDelete = { onDeleteImage(index) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    repeat(3 - state.imageUrls.size) {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }
            Button(
                onClick = openImagePicker,
                enabled = state.imageUrls.size < 3 && !state.isUploading,
                modifier = Modifier.fillMaxWidth(),
                colors =
                ButtonDefaults.buttonColors(
                    containerColor = KoinTheme.colors.primary100,
                    contentColor = KoinTheme.colors.primary600
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(12.dp)
            ) {
                Text(
                    text =
                    stringResource(
                        if (state.isUploading) {
                            Res.string.menu_image_uploading
                        } else {
                            Res.string.menu_image_add
                        }
                    ),
                    style = KoinTheme.typography.medium15
                )
            }
        }
        state.error?.let { error ->
            Text(
                text =
                stringResource(
                    when (error) {
                        MenuFormError.CategoryLoad -> Res.string.error_menu_category_load
                        MenuFormError.DetailLoad -> Res.string.error_menu_detail_load
                        MenuFormError.ImageUpload -> Res.string.error_image_upload
                        MenuFormError.NameRequired -> Res.string.error_menu_name_required
                        MenuFormError.CategoryRequired -> Res.string.error_menu_category_required
                        MenuFormError.PriceInvalid -> Res.string.error_menu_price_invalid
                        MenuFormError.Save -> Res.string.error_menu_save
                    }
                ),
                style = KoinTheme.typography.regular13,
                color = KoinTheme.colors.danger600
            )
        }
        Button(
            onClick = onSave,
            enabled = !state.isSaving && !state.isUploading,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = KoinTheme.colors.primary500),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(14.dp)
        ) {
            Text(
                text =
                stringResource(
                    if (state.isSaving) Res.string.menu_saving else Res.string.menu_save
                ),
                style = KoinTheme.typography.medium16,
                color = Color.White
            )
        }
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun PriceTypeButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text,
        style = KoinTheme.typography.medium14,
        color = if (selected) Color.White else KoinTheme.colors.neutral600,
        modifier =
        Modifier
            .background(
                if (selected) KoinTheme.colors.primary500 else KoinTheme.colors.neutral100,
                RoundedCornerShape(8.dp)
            ).noRippleClickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    )
}
