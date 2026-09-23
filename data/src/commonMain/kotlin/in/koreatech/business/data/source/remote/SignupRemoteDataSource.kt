package `in`.koreatech.business.data.source.remote

import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.data.request.signup.CheckCompanyNumberRequest
import `in`.koreatech.business.data.request.signup.OwnerRegisterRequest
import `in`.koreatech.business.data.request.signup.VerificationCodeSmsRequest
import `in`.koreatech.business.data.request.signup.VerificationSmsRequest
import `in`.koreatech.business.data.response.signup.VerificationCodeResponse
import `in`.koreatech.business.data.response.store.StoreSearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

@Inject
@SingleIn(AppScope::class)
class SignupRemoteDataSource(
    private val httpClient: HttpClient
) {
    suspend fun checkAccount(phoneNumber: String) {
        httpClient.get("owners/exists/account") { parameter("account", phoneNumber) }
    }

    suspend fun requestSmsVerification(request: VerificationSmsRequest) {
        httpClient.post("owners/verification/sms") { setBody(request) }
    }

    suspend fun verifySmsCode(request: VerificationCodeSmsRequest): VerificationCodeResponse = httpClient.post("owners/verification/code/sms") { setBody(request) }.body()

    suspend fun checkCompanyNumber(request: CheckCompanyNumberRequest) {
        httpClient.post("owners/exists/company-number") { setBody(request) }
    }

    suspend fun registerOwner(
        request: OwnerRegisterRequest,
        verificationToken: String
    ) {
        httpClient.post("owners/register/phone") {
            bearerAuth(verificationToken)
            setBody(request)
        }
    }

    suspend fun searchStores(query: String): StoreSearchResponse = httpClient
        .get("v2/shops") {
            parameter("query", query.takeIf(String::isNotBlank))
        }.body()
}
