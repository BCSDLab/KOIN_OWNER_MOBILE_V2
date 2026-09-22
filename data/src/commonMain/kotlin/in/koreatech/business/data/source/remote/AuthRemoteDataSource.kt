package `in`.koreatech.business.data.source.remote

import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.data.request.auth.OwnerLoginRequest
import `in`.koreatech.business.data.request.auth.PasswordResetCodeRequest
import `in`.koreatech.business.data.request.auth.PasswordResetPhoneRequest
import `in`.koreatech.business.data.request.auth.PasswordResetRequest
import `in`.koreatech.business.data.response.auth.OwnerLoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody

@Inject
@SingleIn(AppScope::class)
class AuthRemoteDataSource(
    private val httpClient: HttpClient
) {
    suspend fun signIn(request: OwnerLoginRequest): OwnerLoginResponse = httpClient.post("owner/login") { setBody(request) }.body()

    suspend fun deleteOwner() {
        httpClient.delete("user")
    }

    suspend fun sendPasswordResetSms(request: PasswordResetPhoneRequest) {
        httpClient.post("owners/password/reset/verification/sms") { setBody(request) }
    }

    suspend fun verifyPasswordResetSms(request: PasswordResetCodeRequest) {
        httpClient.post("owners/password/reset/send/sms") { setBody(request) }
    }

    suspend fun resetPassword(request: PasswordResetRequest) {
        httpClient.put("owners/password/reset/sms") { setBody(request) }
    }
}
