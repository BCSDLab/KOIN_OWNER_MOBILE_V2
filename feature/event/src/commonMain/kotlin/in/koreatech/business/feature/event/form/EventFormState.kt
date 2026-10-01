package `in`.koreatech.business.feature.event.form

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class EventFormState(
    val isEdit: Boolean = false,
    val title: String = "",
    val content: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val imageUrls: ImmutableList<String> = persistentListOf(),
    val pendingImageCount: Int = 0,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val error: EventFormError? = null
) {
    val isUploading: Boolean
        get() = pendingImageCount > 0
}
