package `in`.koreatech.business.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dev.zacsweers.metrox.viewmodel.metroViewModel
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.bottom_navigation_event
import `in`.koreatech.business.core.designsystem.generated.resources.bottom_navigation_home
import `in`.koreatech.business.core.designsystem.generated.resources.bottom_navigation_menu
import `in`.koreatech.business.core.designsystem.generated.resources.bottom_navigation_settings
import `in`.koreatech.business.core.designsystem.generated.resources.ic_bottom_event
import `in`.koreatech.business.core.designsystem.generated.resources.ic_bottom_home
import `in`.koreatech.business.core.designsystem.generated.resources.ic_bottom_menu
import `in`.koreatech.business.core.designsystem.generated.resources.ic_bottom_settings
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.core.navigation.MainNavigationEntryProvider
import `in`.koreatech.business.core.navigation.MainNavigator
import `in`.koreatech.business.core.navigation.MainTab
import `in`.koreatech.business.core.navigation.Screen
import `in`.koreatech.business.core.navigation.screenSavedStateConfiguration
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private data class MainTabItem(
    val tab: MainTab,
    val screen: Screen,
    val label: StringResource,
    val icon: DrawableResource
)

private val mainTabItems = listOf(
    MainTabItem(MainTab.Home, Screen.Home, Res.string.bottom_navigation_home, Res.drawable.ic_bottom_home),
    MainTabItem(MainTab.Menu, Screen.Menu, Res.string.bottom_navigation_menu, Res.drawable.ic_bottom_menu),
    MainTabItem(MainTab.Event, Screen.Event, Res.string.bottom_navigation_event, Res.drawable.ic_bottom_event),
    MainTabItem(MainTab.Settings, Screen.Settings, Res.string.bottom_navigation_settings, Res.drawable.ic_bottom_settings)
)

@Composable
fun MainNavigation(viewModel: MainNavigationViewModel = metroViewModel()) {
    val backStacks = mapOf(
        MainTab.Home to rememberNavBackStack(screenSavedStateConfiguration, Screen.Home),
        MainTab.Menu to rememberNavBackStack(screenSavedStateConfiguration, Screen.Menu),
        MainTab.Event to rememberNavBackStack(screenSavedStateConfiguration, Screen.Event),
        MainTab.Settings to rememberNavBackStack(screenSavedStateConfiguration, Screen.Settings)
    )
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(MainTab.Home.ordinal) }
    val selectedTab = mainTabItems[selectedTabIndex]
    val currentBackStack = requireNotNull(backStacks[selectedTab.tab])

    Scaffold(
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(WindowInsets.statusBars),
        bottomBar = {
            if (currentBackStack.lastOrNull() == selectedTab.screen) {
                MainNavigationBar(
                    selectedTab = selectedTab,
                    onTabClick = { selectedTabIndex = it.tab.ordinal }
                )
            }
        }
    ) { paddingValues ->
        MainNavDisplay(
            modifier = Modifier.padding(paddingValues).consumeWindowInsets(WindowInsets.navigationBars),
            backStack = currentBackStack,
            entryProviders = viewModel.mainEntryProviders,
            onSelectTab = { selectedTabIndex = it.ordinal }
        )
    }
}

@Composable
private fun MainNavigationBar(
    selectedTab: MainTabItem,
    onTabClick: (MainTabItem) -> Unit
) {
    NavigationBar(containerColor = KoinTheme.colors.neutral200, tonalElevation = 8.dp) {
        mainTabItems.forEach { tab ->
            val selected = tab == selectedTab
            NavigationBarItem(
                selected = selected,
                onClick = { onTabClick(tab) },
                icon = {
                    Image(
                        painter = painterResource(tab.icon),
                        contentDescription = stringResource(tab.label),
                        colorFilter = ColorFilter.tint(
                            if (selected) KoinTheme.colors.primary500 else KoinTheme.colors.neutral500
                        ),
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = stringResource(tab.label),
                        style = KoinTheme.typography.medium12
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = KoinTheme.colors.primary600,
                    selectedTextColor = KoinTheme.colors.primary600,
                    unselectedIconColor = KoinTheme.colors.neutral500,
                    unselectedTextColor = KoinTheme.colors.neutral500,
                    indicatorColor = KoinTheme.colors.primary100
                )
            )
        }
    }
}

@Composable
private fun MainNavDisplay(
    backStack: NavBackStack<NavKey>,
    entryProviders: Set<MainNavigationEntryProvider>,
    onSelectTab: (MainTab) -> Unit,
    modifier: Modifier = Modifier
) {
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = mainEntryProvider(
            entryProviders = entryProviders,
            navigator = MainNavigator(
                navigate = backStack::add,
                navigateBack = { backStack.removeLastOrNull() },
                selectTab = onSelectTab
            )
        )
    )
}

private fun mainEntryProvider(
    entryProviders: Set<MainNavigationEntryProvider>,
    navigator: MainNavigator
): (NavKey) -> NavEntry<NavKey> = entryProvider {
    entryProviders.forEach { provider ->
        with(provider) { provideEntries(navigator) }
    }
}
