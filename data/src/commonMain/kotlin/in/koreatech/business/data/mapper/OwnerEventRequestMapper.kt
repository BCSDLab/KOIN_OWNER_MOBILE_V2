package `in`.koreatech.business.data.mapper

import `in`.koreatech.business.data.request.store.OwnerEventRequest
import `in`.koreatech.business.domain.model.store.OwnerEventForm

internal fun OwnerEventForm.toOwnerEventRequest(): OwnerEventRequest = OwnerEventRequest(
    title = title,
    content = content,
    thumbnailImages = imageUrls,
    startDate = startDate,
    endDate = endDate
)
