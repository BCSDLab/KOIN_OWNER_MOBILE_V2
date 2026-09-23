package `in`.koreatech.business.data.repository

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.data.mapper.toAuthTokens
import `in`.koreatech.business.data.request.auth.OwnerLoginRequest
import `in`.koreatech.business.data.request.auth.PasswordResetCodeRequest
import `in`.koreatech.business.data.request.auth.PasswordResetPhoneRequest
import `in`.koreatech.business.data.request.auth.PasswordResetRequest
import `in`.koreatech.business.data.source.remote.AuthRemoteDataSource
import `in`.koreatech.business.data.util.suspendRunCatching
import `in`.koreatech.business.domain.model.auth.AuthTokens
import `in`.koreatech.business.domain.repository.AuthRepository

@ContributesBinding(AppScope::class)
@Inject
@SingleIn(AppScope::class)
class AuthRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun signIn(
        phoneNumber: String,
        password: String
    ): Result<AuthTokens> = suspendRunCatching {
        authRemoteDataSource.signIn(OwnerLoginRequest(phoneNumber, password)).toAuthTokens()
    }

    override suspend fun deleteOwner(): Result<Unit> = suspendRunCatching {
        authRemoteDataSource.deleteOwner()
    }

    override suspend fun sendPasswordResetSms(phoneNumber: String): Result<Unit> = suspendRunCatching {
        authRemoteDataSource.sendPasswordResetSms(PasswordResetPhoneRequest(phoneNumber))
    }

    override suspend fun verifyPasswordResetSms(
        phoneNumber: String,
        code: String
    ): Result<Unit> = suspendRunCatching {
        authRemoteDataSource.verifyPasswordResetSms(PasswordResetCodeRequest(phoneNumber, code))
    }

    override suspend fun resetPassword(
        phoneNumber: String,
        password: String
    ): Result<Unit> = suspendRunCatching {
        authRemoteDataSource.resetPassword(PasswordResetRequest(phoneNumber, password))
    }
}
