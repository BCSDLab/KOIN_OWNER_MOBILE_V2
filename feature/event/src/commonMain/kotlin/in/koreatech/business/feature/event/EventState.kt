package `in`.koreatech.business.feature.event

import `in`.koreatech.business.domain.model.store.OwnerEvent
import `in`.koreatech.business.domain.model.store.OwnerShop
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class EventState(
    val shop: OwnerShop? = null,
    val events: ImmutableList<OwnerEvent> = persistentListOf(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isDeleting: Boolean = false,
    val deleteEventId: Int? = null,
    val deleteEventTitle: String? = null
)
