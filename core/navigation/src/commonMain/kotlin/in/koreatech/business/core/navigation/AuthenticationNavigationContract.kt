package `in`.koreatech.business.core.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey

class AuthenticationNavigator(
    val navigate: (Screen) -> Unit,
    val navigateBack: () -> Unit,
    val finishFlow: () -> Unit
)

interface AuthenticationNavigationEntryProvider {
    fun EntryProviderScope<NavKey>.provideEntries(navigator: AuthenticationNavigator)
}
