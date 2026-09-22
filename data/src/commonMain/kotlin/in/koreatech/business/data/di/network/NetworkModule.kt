package `in`.koreatech.business.data.di.network

import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.data.network.createKoinHttpClient
import `in`.koreatech.business.data.source.local.TokenLocalDataSource
import io.ktor.client.HttpClient

@ContributesTo(AppScope::class)
@BindingContainer
object NetworkModule {
    @Provides
    @SingleIn(AppScope::class)
    fun provideHttpClient(tokenLocalDataSource: TokenLocalDataSource): HttpClient = createKoinHttpClient(tokenLocalDataSource)
}
