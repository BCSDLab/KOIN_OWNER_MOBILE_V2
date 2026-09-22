package `in`.koreatech.business.navigation

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.core.navigation.AuthenticationNavigationEntryProvider
import `in`.koreatech.business.core.navigation.MainNavigationEntryProvider
import kotlinx.collections.immutable.toImmutableSet

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class, binding<ViewModel>())
class MainNavigationViewModel(
    mainEntryProviders: Set<MainNavigationEntryProvider>,
    authenticationEntryProviders: Set<AuthenticationNavigationEntryProvider>
) : ViewModel() {
    val mainEntryProviders = mainEntryProviders.toImmutableSet()
    val authenticationEntryProviders = authenticationEntryProviders.toImmutableSet()
}
