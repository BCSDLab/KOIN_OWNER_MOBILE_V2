package `in`.koreatech.business.core.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.savedstate.SavedStateWriter
import androidx.savedstate.savedState

@Composable
fun rememberSavedStateViewModelCreationExtras(
    vararg keys: Any?,
    defaultArguments: SavedStateWriter.() -> Unit
): CreationExtras {
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
    val defaultExtras = (viewModelStoreOwner as? HasDefaultViewModelProviderFactory)
        ?.defaultViewModelCreationExtras
        ?: CreationExtras.Empty
    return remember(viewModelStoreOwner, *keys) {
        MutableCreationExtras(defaultExtras).apply {
            this[DEFAULT_ARGS_KEY] = savedState(builderAction = defaultArguments)
        }
    }
}
