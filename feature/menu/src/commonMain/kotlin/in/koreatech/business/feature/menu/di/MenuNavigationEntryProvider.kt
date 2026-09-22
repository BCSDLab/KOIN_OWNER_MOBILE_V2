package `in`.koreatech.business.feature.menu.di

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.core.navigation.MainNavigationEntryProvider
import `in`.koreatech.business.core.navigation.MainNavigator
import `in`.koreatech.business.core.navigation.Screen
import `in`.koreatech.business.feature.menu.MenuScreen
import `in`.koreatech.business.feature.menu.form.MenuFormScreen

@Inject
@ContributesIntoSet(AppScope::class)
class MenuNavigationEntryProvider : MainNavigationEntryProvider {
    override fun EntryProviderScope<NavKey>.provideEntries(navigator: MainNavigator) {
        entry<Screen.Menu> {
            MenuScreen(
                onCreateMenu = { navigator.navigate(Screen.MenuCreate(it)) },
                onEditMenu = { shopId, menuId ->
                    navigator.navigate(
                        Screen.MenuEdit(
                            shopId,
                            menuId
                        )
                    )
                }
            )
        }
        entry<Screen.MenuCreate> { key ->
            MenuFormScreen(
                shopId = key.shopId,
                menuId = null,
                onBack = navigator.navigateBack,
                onSaved = navigator.navigateBack
            )
        }
        entry<Screen.MenuEdit> { key ->
            MenuFormScreen(
                shopId = key.shopId,
                menuId = key.menuId,
                onBack = navigator.navigateBack,
                onSaved = navigator.navigateBack
            )
        }
    }
}
