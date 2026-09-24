package `in`.koreatech.business.feature.event

import `in`.koreatech.business.feature.event.model.EventShopUiModel
import `in`.koreatech.business.feature.event.model.EventUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class EventState(
    val shop: EventShopUiModel? = null,
    val events: ImmutableList<EventUiModel> = persistentListOf(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isDeleting: Boolean = false,
    val deleteEventId: Int? = null,
    val deleteEventTitle: String? = null
)
