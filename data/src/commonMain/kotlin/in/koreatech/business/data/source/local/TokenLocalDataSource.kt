package `in`.koreatech.business.data.source.local

import com.russhwolf.settings.Settings
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import `in`.koreatech.business.core.di.AppScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Inject
@SingleIn(AppScope::class)
class TokenLocalDataSource(
    private val settings: Settings
) {
    private val mutableAccessToken = MutableStateFlow(settings.getStringOrNull(ACCESS_TOKEN))

    fun observeAccessToken(): Flow<String?> = mutableAccessToken.asStateFlow()

    fun getAccessToken(): String? = settings.getStringOrNull(ACCESS_TOKEN)

    fun getRefreshToken(): String? = settings.getStringOrNull(REFRESH_TOKEN)

    fun saveTokens(
        accessToken: String,
        refreshToken: String
    ) {
        settings.putString(ACCESS_TOKEN, accessToken)
        settings.putString(REFRESH_TOKEN, refreshToken)
        mutableAccessToken.value = accessToken
    }

    fun clearTokens() {
        settings.remove(ACCESS_TOKEN)
        settings.remove(REFRESH_TOKEN)
        mutableAccessToken.value = null
    }

    private companion object {
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
    }
}
