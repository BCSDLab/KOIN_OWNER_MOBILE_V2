package `in`.koreatech.business.domain.usecase.presignedurl

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.model.upload.PreSignedUrlDomain
import `in`.koreatech.business.domain.repository.UploadRepository
import kotlinx.coroutines.CancellationException

@Inject
class UploadImageUseCase(
    private val repository: UploadRepository
) {
    suspend operator fun invoke(
        domain: PreSignedUrlDomain,
        contentLength: Long,
        contentType: String,
        fileName: String,
        bytes: ByteArray,
        authorizationToken: String? = null
    ): Result<String> = try {
        val preSignedUrl = repository
            .getUploadUrl(
                domain = domain.domain,
                contentLength = contentLength,
                contentType = contentType,
                fileName = fileName,
                authorizationToken = authorizationToken
            ).getOrThrow()

        repository
            .uploadFile(
                url = preSignedUrl.preSignedUrl,
                bytes = bytes,
                mediaType = contentType,
                mediaSize = contentLength
            ).getOrThrow()

        Result.success(preSignedUrl.fileUrl)
    } catch (error: CancellationException) {
        throw error
    } catch (error: Exception) {
        Result.failure(error)
    }
}
