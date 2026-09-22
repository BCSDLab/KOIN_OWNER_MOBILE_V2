package `in`.koreatech.business.core.file

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import kotlinx.coroutines.launch

@Composable
fun rememberImageFilePicker(
    onImagePicked: (PickedImageFile) -> Unit,
    onFailure: (Throwable) -> Unit
): () -> Unit {
    val coroutineScope = rememberCoroutineScope()
    val currentOnImagePicked = rememberUpdatedState(onImagePicked)
    val currentOnFailure = rememberUpdatedState(onFailure)

    return remember(coroutineScope) {
        {
            coroutineScope.launch {
                pickImageFile()
                    .onSuccess { file -> file?.let(currentOnImagePicked.value) }
                    .onFailure(currentOnFailure.value)
            }
        }
    }
}
