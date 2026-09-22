package `in`.koreatech.business.domain.repository

import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    fun observeAccessToken(): Flow<String?>

    suspend fun getAccessToken(): String?

    suspend fun saveTokens(
        accessToken: String,
        refreshToken: String
    )

    suspend fun clearTokens()
}
