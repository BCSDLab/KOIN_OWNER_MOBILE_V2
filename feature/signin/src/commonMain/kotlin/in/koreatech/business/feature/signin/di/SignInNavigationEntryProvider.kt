package `in`.koreatech.business.feature.signin.di

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.core.navigation.AuthenticationNavigationEntryProvider
import `in`.koreatech.business.core.navigation.AuthenticationNavigator
import `in`.koreatech.business.core.navigation.Screen
import `in`.koreatech.business.feature.signin.SignInScreen
import `in`.koreatech.business.feature.signin.password.PasswordResetScreen

@Inject
@ContributesIntoSet(AppScope::class)
class SignInNavigationEntryProvider : AuthenticationNavigationEntryProvider {
    override fun EntryProviderScope<NavKey>.provideEntries(navigator: AuthenticationNavigator) {
        entry<Screen.SignIn> {
            SignInScreen(
                onSignUpClick = { navigator.navigate(Screen.SignUp) },
                onFindPasswordClick = { navigator.navigate(Screen.PasswordReset) }
            )
        }
        entry<Screen.PasswordReset> {
            PasswordResetScreen(
                onBack = navigator.navigateBack,
                onComplete = navigator.finishFlow
            )
        }
    }
}
