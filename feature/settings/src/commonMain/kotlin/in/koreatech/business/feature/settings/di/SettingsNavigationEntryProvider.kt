package `in`.koreatech.business.feature.settings.di

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.core.navigation.MainNavigationEntryProvider
import `in`.koreatech.business.core.navigation.MainNavigator
import `in`.koreatech.business.core.navigation.Screen
import `in`.koreatech.business.feature.settings.SettingsScreen
import `in`.koreatech.business.feature.settings.license.OpenSourceLicensesScreen
import `in`.koreatech.business.feature.settings.terms.TermsScreen

@Inject
@ContributesIntoSet(AppScope::class)
class SettingsNavigationEntryProvider : MainNavigationEntryProvider {
    override fun EntryProviderScope<NavKey>.provideEntries(navigator: MainNavigator) {
        entry<Screen.Settings> {
            SettingsScreen(
                onManageShopsClick = { navigator.navigate(Screen.ManageShops) },
                onTermsClick = { navigator.navigate(Screen.Terms) },
                onOpenSourceLicensesClick = { navigator.navigate(Screen.OpenSourceLicenses) }
            )
        }
        entry<Screen.Terms> { TermsScreen(onBack = navigator.navigateBack) }
        entry<Screen.OpenSourceLicenses> { OpenSourceLicensesScreen(onBack = navigator.navigateBack) }
    }
}
