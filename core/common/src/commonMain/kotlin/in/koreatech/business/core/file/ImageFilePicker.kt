package `in`.koreatech.business.core.file

import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.openFilePicker
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.readBytes
import kotlinx.coroutines.CancellationException

suspend fun pickImageFile(): Result<PickedImageFile?> = try {
    val file = FileKit.openFilePicker(type = FileKitType.Image)
    Result.success(
        file?.let {
            PickedImageFile(
                name = it.name,
                contentType = it.name.toImageContentType(),
                bytes = it.readBytes()
            )
        }
    )
} catch (error: CancellationException) {
    throw error
} catch (error: Throwable) {
    Result.failure(error)
}
