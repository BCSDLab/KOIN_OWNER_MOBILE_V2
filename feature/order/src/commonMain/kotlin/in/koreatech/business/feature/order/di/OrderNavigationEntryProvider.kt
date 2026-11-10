package `in`.koreatech.business.feature.order.di

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.core.navigation.MainNavigationEntryProvider
import `in`.koreatech.business.core.navigation.MainNavigator
import `in`.koreatech.business.core.navigation.Screen
import `in`.koreatech.business.feature.order.OrderDetailScreen
import `in`.koreatech.business.feature.order.OrderScreen
import `in`.koreatech.business.feature.order.OrderServiceScreen

@Inject
@ContributesIntoSet(AppScope::class)
class OrderNavigationEntryProvider : MainNavigationEntryProvider {
    override fun EntryProviderScope<NavKey>.provideEntries(navigator: MainNavigator) {
        entry<Screen.Order> {
            OrderScreen(
                onOrderClick = { orderableShopId, orderId ->
                    navigator.navigate(Screen.OrderDetail(orderableShopId, orderId))
                },
                onRequestOrderService = { navigator.navigate(Screen.OrderServiceRequest) }
            )
        }
        entry<Screen.OrderServiceRequest> {
            OrderServiceScreen(onBack = navigator.navigateBack)
        }
        entry<Screen.OrderDetail> { key ->
            OrderDetailScreen(key.orderableShopId, key.orderId, navigator.navigateBack)
        }
    }
}
