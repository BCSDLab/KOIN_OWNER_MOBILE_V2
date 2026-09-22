package `in`.koreatech.business.feature.store.register

sealed interface RegisterStoreSideEffect {
    data class ShowError(
        val error: RegisterStoreError
    ) : RegisterStoreSideEffect

    data object NavigateToCategory : RegisterStoreSideEffect

    data object NavigateToBasicInfo : RegisterStoreSideEffect

    data object NavigateToDetailInfo : RegisterStoreSideEffect

    data object NavigateToConfirm : RegisterStoreSideEffect

    data object NavigateToComplete : RegisterStoreSideEffect
}
