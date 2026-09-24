package `in`.koreatech.business.core.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey

enum class MainTab {
    Home,
    Menu,
    Event,
    Order,
    Settings
}

class MainNavigator(
    val navigate: (Screen) -> Unit,
    val navigateBack: () -> Unit,
    val selectTab: (MainTab) -> Unit
)

interface MainNavigationEntryProvider {
    fun EntryProviderScope<NavKey>.provideEntries(navigator: MainNavigator)
}
