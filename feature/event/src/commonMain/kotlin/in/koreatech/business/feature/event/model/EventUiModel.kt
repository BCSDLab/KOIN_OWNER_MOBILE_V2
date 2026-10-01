package `in`.koreatech.business.feature.event.model

import `in`.koreatech.business.domain.model.store.OwnerEvent
import `in`.koreatech.business.feature.event.util.epochMillisToDate
import kotlin.time.Clock
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
    val endDate: String,
    val isExpired: Boolean
)

internal fun OwnerEvent.toEventUiModel(
    today: String = epochMillisToDate(Clock.System.now().toEpochMilliseconds())
) = EventUiModel(
    id = id,
    title = title,
    content = content,
    imageUrls = imageUrls.toImmutableList(),
    startDate = startDate,
    endDate = endDate,
    isExpired = endDate < today
)
