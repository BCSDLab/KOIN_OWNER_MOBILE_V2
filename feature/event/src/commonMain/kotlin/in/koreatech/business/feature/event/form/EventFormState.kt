package `in`.koreatech.business.feature.event.form

import androidx.compose.foundation.text.input.TextFieldState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class EventFormState(
    val titleTextFieldState: TextFieldState = TextFieldState(),
    val contentTextFieldState: TextFieldState = TextFieldState(),
    val isEdit: Boolean = false,
    val startDate: String = "",
    val endDate: String = "",
    val imageUrls: ImmutableList<String> = persistentListOf(),
    val pendingImageCount: Int = 0,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val error: EventFormError? = null
) {
    val title: String get() = titleTextFieldState.text.toString()
    val content: String get() = contentTextFieldState.text.toString()

    val isUploading: Boolean
        get() = pendingImageCount > 0
}
