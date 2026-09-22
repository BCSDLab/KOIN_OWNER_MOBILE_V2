package `in`.koreatech.business.feature.event.form

sealed interface EventFormSideEffect {
    data object Saved : EventFormSideEffect
}
