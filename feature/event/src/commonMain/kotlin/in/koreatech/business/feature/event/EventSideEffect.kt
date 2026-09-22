package `in`.koreatech.business.feature.event

sealed interface EventSideEffect {
    data object ShopLoadFailed : EventSideEffect

    data object EventLoadFailed : EventSideEffect

    data object EventReloadFailed : EventSideEffect

    data object EventDeleteFailed : EventSideEffect

    data class NavigateToCreate(
        val shopId: Int
    ) : EventSideEffect

    data class NavigateToEdit(
        val shopId: Int,
        val eventId: Int
    ) : EventSideEffect
}
