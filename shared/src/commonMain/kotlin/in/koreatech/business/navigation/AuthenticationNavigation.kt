package `in`.koreatech.business.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dev.zacsweers.metrox.viewmodel.metroViewModel
import `in`.koreatech.business.core.navigation.AuthenticationNavigationEntryProvider
import `in`.koreatech.business.core.navigation.AuthenticationNavigator
import `in`.koreatech.business.core.navigation.Screen
import `in`.koreatech.business.core.navigation.screenSavedStateConfiguration

@Composable
fun AuthenticationNavigation(viewModel: MainNavigationViewModel = metroViewModel()) {
    val backStack = rememberNavBackStack(screenSavedStateConfiguration, Screen.SignIn)

    NavDisplay(
        backStack = backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = authenticationEntryProvider(
            entryProviders = viewModel.authenticationEntryProviders,
            navigator = AuthenticationNavigator(
                navigate = backStack::add,
                navigateBack = { backStack.removeLastOrNull() },
                finishFlow = {
                    backStack.clear()
                    backStack.add(Screen.SignIn)
                }
            )
        ),
        transitionSpec = {
            slideInHorizontally(initialOffsetX = { it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { -it })
        },
        popTransitionSpec = {
            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { it })
        },
        predictivePopTransitionSpec = {
            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { it })
        }
    )
}

private fun authenticationEntryProvider(
    entryProviders: Set<AuthenticationNavigationEntryProvider>,
    navigator: AuthenticationNavigator
): (NavKey) -> NavEntry<NavKey> = entryProvider {
    entryProviders.forEach { provider ->
        with(provider) { provideEntries(navigator) }
    }
}
