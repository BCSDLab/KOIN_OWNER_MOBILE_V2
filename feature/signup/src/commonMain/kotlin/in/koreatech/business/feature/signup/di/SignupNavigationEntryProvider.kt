package `in`.koreatech.business.feature.signup.di

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.core.navigation.AuthenticationNavigationEntryProvider
import `in`.koreatech.business.core.navigation.AuthenticationNavigator
import `in`.koreatech.business.core.navigation.Screen
import `in`.koreatech.business.feature.signup.SignupScreen

@Inject
@ContributesIntoSet(AppScope::class)
class SignupNavigationEntryProvider : AuthenticationNavigationEntryProvider {
    override fun EntryProviderScope<NavKey>.provideEntries(navigator: AuthenticationNavigator) {
        entry<Screen.SignUp> {
            SignupScreen(
                onBack = navigator.navigateBack,
                onComplete = navigator.finishFlow
            )
        }
    }
}
