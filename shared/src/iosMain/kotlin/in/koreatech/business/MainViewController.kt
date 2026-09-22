package `in`.koreatech.business

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import dev.zacsweers.metrox.viewmodel.LocalMetroViewModelFactory
import `in`.koreatech.business.di.createIosAppGraph
import platform.UIKit.UIViewController

@Suppress("FunctionName")
fun MainViewController(): UIViewController {
    val appGraph = createIosAppGraph()

    return ComposeUIViewController {
        CompositionLocalProvider(
            LocalMetroViewModelFactory provides appGraph.metroViewModelFactory
        ) {
            App()
        }
    }
}
