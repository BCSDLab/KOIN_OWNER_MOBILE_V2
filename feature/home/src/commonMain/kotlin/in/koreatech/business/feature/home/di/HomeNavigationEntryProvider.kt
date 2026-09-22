package `in`.koreatech.business.feature.home.di

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.core.navigation.MainNavigationEntryProvider
import `in`.koreatech.business.core.navigation.MainNavigator
import `in`.koreatech.business.core.navigation.MainTab
import `in`.koreatech.business.core.navigation.Screen
import `in`.koreatech.business.feature.home.HomeScreen

@Inject
@ContributesIntoSet(AppScope::class)
class HomeNavigationEntryProvider : MainNavigationEntryProvider {
    override fun EntryProviderScope<NavKey>.provideEntries(navigator: MainNavigator) {
        entry<Screen.Home> {
            HomeScreen(
                onShopClick = { navigator.navigate(Screen.StoreEdit(it)) },
                onRegisterShopClick = { navigator.navigate(Screen.RegisterStore) },
                onMenuClick = { navigator.selectTab(MainTab.Menu) },
                onEventClick = { navigator.selectTab(MainTab.Event) }
            )
        }
    }
}
