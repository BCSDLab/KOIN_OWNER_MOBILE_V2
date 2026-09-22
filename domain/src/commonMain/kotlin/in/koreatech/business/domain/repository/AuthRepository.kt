package `in`.koreatech.business.domain.repository

import `in`.koreatech.business.domain.model.auth.AuthTokens

interface AuthRepository {
    suspend fun signIn(
        phoneNumber: String,
        password: String
    ): Result<AuthTokens>

    suspend fun deleteOwner(): Result<Unit>

    suspend fun sendPasswordResetSms(phoneNumber: String): Result<Unit>

    suspend fun verifyPasswordResetSms(
        phoneNumber: String,
        code: String
    ): Result<Unit>

    suspend fun resetPassword(
        phoneNumber: String,
        password: String
    ): Result<Unit>
}
