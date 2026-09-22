package `in`.koreatech.business

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import dev.zacsweers.metrox.viewmodel.metroViewModel
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.loading.GlobalLoadingScreen
import `in`.koreatech.business.navigation.AuthenticationNavigation
import `in`.koreatech.business.navigation.MainNavigation
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun App() {
    KoinTheme {
        val viewModel = metroViewModel<AppViewModel>()
        val state by viewModel.collectAsState()

        when (val currentState = state) {
            AppState.Loading -> GlobalLoadingScreen()
            is AppState.Ready -> {
                if (currentState.accessToken == null) {
                    AuthenticationNavigation()
                } else {
                    MainNavigation()
                }
            }
        }
    }
}
