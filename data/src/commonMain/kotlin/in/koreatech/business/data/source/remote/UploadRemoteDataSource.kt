package `in`.koreatech.business.data.source.remote

import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.data.request.upload.UploadUrlRequest
import `in`.koreatech.business.data.response.upload.UploadUrlResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders

@Inject
@SingleIn(AppScope::class)
class UploadRemoteDataSource(
    private val httpClient: HttpClient
) {
    suspend fun getUploadUrl(
        domain: String,
        request: UploadUrlRequest,
        authorizationToken: String?
    ): UploadUrlResponse = httpClient
        .post("$domain/upload/url") {
            authorizationToken?.let(::bearerAuth)
            setBody(request)
        }.body()

    suspend fun uploadFile(
        url: String,
        bytes: ByteArray,
        mediaType: String,
        mediaSize: Long
    ) {
        httpClient.put(url) {
            header(HttpHeaders.ContentType, ContentType.parse(mediaType))
            header(HttpHeaders.ContentLength, mediaSize)
            setBody(bytes)
        }
    }
}
