package `in`.koreatech.business.data.mapper

import `in`.koreatech.business.data.response.store.OwnerEventResponse
import `in`.koreatech.business.domain.model.store.OwnerEvent
internal fun OwnerEventResponse.toOwnerEvent(): OwnerEvent = OwnerEvent(
    id = eventId,
    shopId = shopId,
    shopName = shopName,
    title = title,
    content = content,
    imageUrls = thumbnailImages,
    startDate = startDate,
    endDate = endDate
)
