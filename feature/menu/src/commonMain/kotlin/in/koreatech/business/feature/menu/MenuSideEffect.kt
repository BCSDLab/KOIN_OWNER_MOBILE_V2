package `in`.koreatech.business.feature.menu

sealed interface MenuSideEffect {
    data class NavigateToCreate(
        val shopId: Int
    ) : MenuSideEffect

    data class NavigateToEdit(
        val shopId: Int,
        val menuId: Int
    ) : MenuSideEffect

    data class ShowError(
        val error: MenuError
    ) : MenuSideEffect
}
