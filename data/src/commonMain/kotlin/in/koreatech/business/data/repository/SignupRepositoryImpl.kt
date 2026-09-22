package `in`.koreatech.business.data.repository

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.data.mapper.toOwnerRegisterRequest
import `in`.koreatech.business.data.mapper.toStoreSearchResult
import `in`.koreatech.business.data.request.signup.CheckCompanyNumberRequest
import `in`.koreatech.business.data.request.signup.VerificationCodeSmsRequest
import `in`.koreatech.business.data.request.signup.VerificationSmsRequest
import `in`.koreatech.business.data.source.remote.SignupRemoteDataSource
import `in`.koreatech.business.data.util.suspendRunCatching
import `in`.koreatech.business.domain.error.ApiException
import `in`.koreatech.business.domain.error.signup.PhoneNumberAlreadyExistsException
import `in`.koreatech.business.domain.model.signup.OwnerRegistration
import `in`.koreatech.business.domain.model.store.StoreSearchResult
import `in`.koreatech.business.domain.repository.SignupRepository
import kotlinx.coroutines.CancellationException

@ContributesBinding(AppScope::class)
@Inject
@SingleIn(AppScope::class)
class SignupRepositoryImpl(
    private val signupRemoteDataSource: SignupRemoteDataSource
) : SignupRepository {
    override suspend fun checkAccount(phoneNumber: String): Result<Unit> =
        try {
            signupRemoteDataSource.checkAccount(phoneNumber)
            Result.success(Unit)
        } catch (exception: ApiException) {
            if (exception.statusCode == 409) {
                Result.failure(PhoneNumberAlreadyExistsException())
            } else {
                Result.failure(exception)
            }
        } catch (exception: CancellationException) {
            throw exception
        } catch (throwable: Throwable) {
            Result.failure(throwable)
        }

    override suspend fun requestSmsVerification(phoneNumber: String): Result<Unit> =
        suspendRunCatching {
            signupRemoteDataSource.requestSmsVerification(VerificationSmsRequest(phoneNumber))
        }

    override suspend fun verifySmsCode(
        phoneNumber: String,
        verificationCode: String
    ): Result<String> =
        suspendRunCatching {
            signupRemoteDataSource
                .verifySmsCode(
                    VerificationCodeSmsRequest(phoneNumber, verificationCode)
                ).token
        }

    override suspend fun checkCompanyNumber(companyNumber: String): Result<Unit> =
        suspendRunCatching {
            signupRemoteDataSource.checkCompanyNumber(CheckCompanyNumberRequest(companyNumber))
        }

    override suspend fun registerOwner(
        registration: OwnerRegistration,
        verificationToken: String
    ): Result<Unit> =
        suspendRunCatching {
            signupRemoteDataSource.registerOwner(registration.toOwnerRegisterRequest(), verificationToken)
        }

    override suspend fun searchStores(query: String): Result<List<StoreSearchResult>> =
        suspendRunCatching {
            signupRemoteDataSource.searchStores(query).shops.map { it.toStoreSearchResult() }
        }
}
