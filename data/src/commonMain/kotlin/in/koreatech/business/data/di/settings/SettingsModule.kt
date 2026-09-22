package `in`.koreatech.business.data.di.settings

import com.russhwolf.settings.Settings
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import `in`.koreatech.business.core.di.AppScope

@ContributesTo(AppScope::class)
@BindingContainer
object SettingsModule {
    @Provides
    @SingleIn(AppScope::class)
    fun provideSettings(): Settings = Settings()
}
