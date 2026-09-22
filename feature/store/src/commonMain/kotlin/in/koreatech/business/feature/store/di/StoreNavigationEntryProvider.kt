package `in`.koreatech.business.feature.store.di

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.core.navigation.MainNavigationEntryProvider
import `in`.koreatech.business.core.navigation.MainNavigator
import `in`.koreatech.business.core.navigation.Screen
import `in`.koreatech.business.feature.store.manage.ManageShopsScreen
import `in`.koreatech.business.feature.store.register.RegisterStoreScreen

@Inject
@ContributesIntoSet(AppScope::class)
class StoreNavigationEntryProvider : MainNavigationEntryProvider {
    override fun EntryProviderScope<NavKey>.provideEntries(navigator: MainNavigator) {
        entry<Screen.RegisterStore> {
            RegisterStoreScreen(
                onBack = navigator.navigateBack,
                onComplete = navigator.navigateBack
            )
        }
        entry<Screen.ManageShops> {
            ManageShopsScreen(
                onBack = navigator.navigateBack,
                onEditShopClick = { navigator.navigate(Screen.StoreEdit(it)) },
                onRegisterShopClick = { navigator.navigate(Screen.RegisterStore) }
            )
        }
        entry<Screen.StoreEdit> { key ->
            RegisterStoreScreen(
                shopId = key.shopId,
                onBack = navigator.navigateBack,
                onComplete = navigator.navigateBack
            )
        }
    }
}
