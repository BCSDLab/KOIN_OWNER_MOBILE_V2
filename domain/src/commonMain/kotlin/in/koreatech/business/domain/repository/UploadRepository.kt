package `in`.koreatech.business.domain.repository

import `in`.koreatech.business.domain.model.upload.PreSignedUrl

interface UploadRepository {
    suspend fun getUploadUrl(
        domain: String,
        contentLength: Long,
        contentType: String,
        fileName: String,
        authorizationToken: String? = null
    ): Result<PreSignedUrl>

    suspend fun uploadFile(
        url: String,
        bytes: ByteArray,
        mediaType: String,
        mediaSize: Long
    ): Result<Unit>
}
