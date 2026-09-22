package `in`.koreatech.business.feature.menu.form

sealed interface MenuFormSideEffect {
    data object Saved : MenuFormSideEffect
}
