package `in`.koreatech.business.feature.event.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.unit.dp
import dev.zacsweers.metrox.viewmodel.assistedMetroViewModel
import `in`.koreatech.business.core.designsystem.component.KoinImagePreview
import `in`.koreatech.business.core.designsystem.component.KoinUnderlineTextField
import `in`.koreatech.business.core.designsystem.component.button.primaryButtonColors
import `in`.koreatech.business.core.designsystem.component.button.secondaryButtonColors
import `in`.koreatech.business.core.designsystem.component.topbar.KoinTopAppBar
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.error_event_content_required
import `in`.koreatech.business.core.designsystem.generated.resources.error_event_date_invalid
import `in`.koreatech.business.core.designsystem.generated.resources.error_event_load
import `in`.koreatech.business.core.designsystem.generated.resources.error_event_save
import `in`.koreatech.business.core.designsystem.generated.resources.error_event_title_required
import `in`.koreatech.business.core.designsystem.generated.resources.error_image_upload
import `in`.koreatech.business.core.designsystem.generated.resources.event_content
import `in`.koreatech.business.core.designsystem.generated.resources.event_content_hint
import `in`.koreatech.business.core.designsystem.generated.resources.event_date_end
import `in`.koreatech.business.core.designsystem.generated.resources.event_date_hint
import `in`.koreatech.business.core.designsystem.generated.resources.event_date_start
import `in`.koreatech.business.core.designsystem.generated.resources.event_edit_title
import `in`.koreatech.business.core.designsystem.generated.resources.event_form_title
import `in`.koreatech.business.core.designsystem.generated.resources.event_image_add
import `in`.koreatech.business.core.designsystem.generated.resources.event_image_count
import `in`.koreatech.business.core.designsystem.generated.resources.event_image_description
import `in`.koreatech.business.core.designsystem.generated.resources.event_image_uploading
import `in`.koreatech.business.core.designsystem.generated.resources.event_save
import `in`.koreatech.business.core.designsystem.generated.resources.event_saving
import `in`.koreatech.business.core.designsystem.generated.resources.event_title_input
import `in`.koreatech.business.core.designsystem.generated.resources.event_title_input_hint
import `in`.koreatech.business.core.designsystem.generated.resources.event_update
import `in`.koreatech.business.core.designsystem.generated.resources.event_updating
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.core.file.rememberImageFilePicker
import `in`.koreatech.business.core.viewmodel.rememberSavedStateViewModelCreationExtras
import `in`.koreatech.business.feature.event.form.component.EventDatePickerField
import org.jetbrains.compose.resources.stringResource
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun EventFormScreen(
    shopId: Int,
    eventId: Int? = null,
    onBack: () -> Unit,
    onSaved: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EventFormViewModel = eventFormViewModel(shopId, eventId)
) {
    val state by viewModel.collectAsState()
    viewModel.collectSideEffect { onSaved() }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            KoinTopAppBar(
                title = {
                    Text(
                        text =
                        stringResource(
                            if (state.isEdit) Res.string.event_edit_title else Res.string.event_form_title
                        ),
                        style = KoinTheme.typography.medium18
                    )
                },
                onNavigationIconClick = onBack
            )
        },
        containerColor = KoinTheme.colors.neutral75
    ) { paddingValues ->
        EventFormScreenImpl(
            state = state,
            onTitleChange = viewModel::updateTitle,
            onContentChange = viewModel::updateContent,
            onStartDateChange = viewModel::updateStartDate,
            onEndDateChange = viewModel::updateEndDate,
            onUploadImage = viewModel::uploadImage,
            onImageSelectionFailed = viewModel::onImageSelectionFailed,
            onDeleteImage = viewModel::deleteImage,
            onSave = viewModel::save,
            modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

@Composable
private fun eventFormViewModel(
    shopId: Int,
    eventId: Int?
): EventFormViewModel {
    val extras =
        rememberSavedStateViewModelCreationExtras(shopId, eventId) {
            putInt(EventFormViewModel.SHOP_ID_KEY, shopId)
            eventId?.let { putInt(EventFormViewModel.EVENT_ID_KEY, it) }
        }
    return assistedMetroViewModel(extras = extras)
}

@Composable
fun EventFormScreenImpl(
    state: EventFormState,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onStartDateChange: (String) -> Unit,
    onEndDateChange: (String) -> Unit,
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
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(color = KoinTheme.colors.primary500)
        }
        return
    }
    Column(
        modifier =
        modifier
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        KoinUnderlineTextField(
            title = { Text(stringResource(Res.string.event_title_input), style = KoinTheme.typography.medium15) },
            value = state.title,
            onValueChange = onTitleChange,
            placeholder = stringResource(Res.string.event_title_input_hint)
        )
        KoinUnderlineTextField(
            title = { Text(stringResource(Res.string.event_content), style = KoinTheme.typography.medium15) },
            value = state.content,
            onValueChange = onContentChange,
            placeholder = stringResource(Res.string.event_content_hint),
            singleLine = false
        )
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            EventDatePickerField(
                title = stringResource(Res.string.event_date_start),
                value = state.startDate,
                placeholder = stringResource(Res.string.event_date_hint),
                onDateSelected = onStartDateChange,
                modifier = Modifier.weight(1f)
            )
            EventDatePickerField(
                title = stringResource(Res.string.event_date_end),
                value = state.endDate,
                placeholder = stringResource(Res.string.event_date_hint),
                onDateSelected = onEndDateChange,
                modifier = Modifier.weight(1f),
                minimumDate = state.startDate.takeIf(String::isNotEmpty)
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(Res.string.event_image_count, state.imageUrls.size),
                style = KoinTheme.typography.medium15
            )
            if (state.imageUrls.isNotEmpty()) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    state.imageUrls.forEachIndexed { index, imageUrl ->
                        KoinImagePreview(
                            imageUrl = imageUrl,
                            contentDescription =
                            stringResource(
                                Res.string.event_image_description,
                                index + 1
                            ),
                            onDelete = { onDeleteImage(index) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    repeat(3 - state.imageUrls.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            Button(
                onClick = openImagePicker,
                enabled = state.imageUrls.size < 3 && !state.isUploading,
                modifier = Modifier.fillMaxWidth(),
                colors = secondaryButtonColors(),
                shape = KoinTheme.shapes.large,
                contentPadding = PaddingValues(8.dp)
            ) {
                Text(
                    text =
                    stringResource(
                        if (state.isUploading) {
                            Res.string.event_image_uploading
                        } else {
                            Res.string.event_image_add
                        }
                    ),
                    style = KoinTheme.typography.regular14
                )
            }
        }
        state.error?.let { error ->
            Text(
                text =
                stringResource(
                    when (error) {
                        EventFormError.Load -> Res.string.error_event_load
                        EventFormError.TitleRequired -> Res.string.error_event_title_required
                        EventFormError.ContentRequired -> Res.string.error_event_content_required
                        EventFormError.DateInvalid -> Res.string.error_event_date_invalid
                        EventFormError.ImageUpload -> Res.string.error_image_upload
                        EventFormError.Save -> Res.string.error_event_save
                    }
                ),
                style = KoinTheme.typography.regular13,
                color = KoinTheme.colors.danger600
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onSave,
            enabled = !state.isSaving && !state.isUploading,
            modifier = Modifier.fillMaxWidth(),
            colors = primaryButtonColors(),
            shape = KoinTheme.shapes.small,
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            Text(
                text =
                stringResource(
                    when {
                        state.isSaving && state.isEdit -> Res.string.event_updating
                        state.isSaving -> Res.string.event_saving
                        state.isEdit -> Res.string.event_update
                        else -> Res.string.event_save
                    }
                ),
                style = KoinTheme.typography.medium16,
                color = Color.White
            )
        }
    }
}
