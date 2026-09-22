package `in`.koreatech.business.feature.event.form.mapper

import `in`.koreatech.business.domain.model.store.OwnerEvent
import `in`.koreatech.business.domain.model.store.OwnerEventForm
import `in`.koreatech.business.feature.event.form.EventFormState
import kotlinx.collections.immutable.toImmutableList

internal fun EventFormState.withOwnerEvent(event: OwnerEvent) =
    copy(
        title = event.title,
        content = event.content,
        startDate = event.startDate,
        endDate = event.endDate,
        imageUrls = event.imageUrls.toImmutableList(),
        isLoading = false,
        error = null
    )

internal fun EventFormState.toOwnerEventForm() =
    OwnerEventForm(
        title = title.trim(),
        content = content.trim(),
        imageUrls = imageUrls,
        startDate = startDate,
        endDate = endDate
    )
