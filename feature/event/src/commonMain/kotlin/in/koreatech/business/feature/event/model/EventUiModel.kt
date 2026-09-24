package `in`.koreatech.business.feature.event.model

import `in`.koreatech.business.domain.model.store.OwnerEvent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class EventShopUiModel(
    val id: Int,
    val name: String
)

data class EventUiModel(
    val id: Int,
    val title: String,
    val content: String,
    val imageUrls: ImmutableList<String>,
    val startDate: String,
    val endDate: String
)

internal fun OwnerEvent.toEventUiModel() = EventUiModel(
    id = id,
    title = title,
    content = content,
    imageUrls = imageUrls.toImmutableList(),
    startDate = startDate,
    endDate = endDate
)
