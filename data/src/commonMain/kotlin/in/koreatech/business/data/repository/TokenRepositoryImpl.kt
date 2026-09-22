package `in`.koreatech.business.data.repository

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.data.source.local.TokenLocalDataSource
import `in`.koreatech.business.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow

@ContributesBinding(AppScope::class)
@Inject
@SingleIn(AppScope::class)
class TokenRepositoryImpl(
    private val tokenLocalDataSource: TokenLocalDataSource
) : TokenRepository {
    override fun observeAccessToken(): Flow<String?> = tokenLocalDataSource.observeAccessToken()

    override suspend fun getAccessToken(): String? = tokenLocalDataSource.getAccessToken()

    override suspend fun saveTokens(
        accessToken: String,
        refreshToken: String
    ) {
        tokenLocalDataSource.saveTokens(accessToken, refreshToken)
    }

    override suspend fun clearTokens() {
        tokenLocalDataSource.clearTokens()
    }
}
