package `in`.koreatech.business.data.repository

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.data.mapper.toPreSignedUrl
import `in`.koreatech.business.data.request.upload.UploadUrlRequest
import `in`.koreatech.business.data.source.remote.UploadRemoteDataSource
import `in`.koreatech.business.data.util.suspendRunCatching
import `in`.koreatech.business.domain.model.upload.PreSignedUrl
import `in`.koreatech.business.domain.repository.UploadRepository

@ContributesBinding(AppScope::class)
@Inject
@SingleIn(AppScope::class)
class UploadRepositoryImpl(
    private val uploadRemoteDataSource: UploadRemoteDataSource
) : UploadRepository {
    override suspend fun getUploadUrl(
        domain: String,
        contentLength: Long,
        contentType: String,
        fileName: String,
        authorizationToken: String?
    ): Result<PreSignedUrl> = suspendRunCatching {
        uploadRemoteDataSource
            .getUploadUrl(
                domain = domain,
                request = UploadUrlRequest(contentLength, contentType, fileName),
                authorizationToken = authorizationToken
            ).toPreSignedUrl()
    }

    override suspend fun uploadFile(
        url: String,
        bytes: ByteArray,
        mediaType: String,
        mediaSize: Long
    ): Result<Unit> = suspendRunCatching {
        uploadRemoteDataSource.uploadFile(url, bytes, mediaType, mediaSize)
    }
}
