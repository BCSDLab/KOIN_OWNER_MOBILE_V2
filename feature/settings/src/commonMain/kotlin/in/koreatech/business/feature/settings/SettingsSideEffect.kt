package `in`.koreatech.business.feature.settings

sealed interface SettingsSideEffect {
    data object DeleteOwnerFailed : SettingsSideEffect
}
