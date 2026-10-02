package `in`.koreatech.business.feature.event.form.mapper

import androidx.compose.foundation.text.input.TextFieldState
import `in`.koreatech.business.domain.model.store.OwnerEvent
import `in`.koreatech.business.domain.model.store.OwnerEventForm
import `in`.koreatech.business.feature.event.form.EventFormState
import kotlinx.collections.immutable.toImmutableList

internal fun EventFormState.withOwnerEvent(event: OwnerEvent) = copy(
    titleTextFieldState = TextFieldState(event.title),
    contentTextFieldState = TextFieldState(event.content),
    startDate = event.startDate,
    endDate = event.endDate,
    imageUrls = event.imageUrls.toImmutableList(),
    isLoading = false,
    error = null
)

internal fun EventFormState.toOwnerEventForm() = OwnerEventForm(
    title = title.trim(),
    content = content.trim(),
    imageUrls = imageUrls,
    startDate = startDate,
    endDate = endDate
)
