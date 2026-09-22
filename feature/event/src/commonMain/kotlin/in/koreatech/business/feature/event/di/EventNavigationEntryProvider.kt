package `in`.koreatech.business.feature.event.di

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.core.navigation.MainNavigationEntryProvider
import `in`.koreatech.business.core.navigation.MainNavigator
import `in`.koreatech.business.core.navigation.Screen
import `in`.koreatech.business.feature.event.EventScreen
import `in`.koreatech.business.feature.event.form.EventFormScreen

@Inject
@ContributesIntoSet(AppScope::class)
class EventNavigationEntryProvider : MainNavigationEntryProvider {
    override fun EntryProviderScope<NavKey>.provideEntries(navigator: MainNavigator) {
        entry<Screen.Event> {
            EventScreen(
                onCreateEvent = { navigator.navigate(Screen.EventCreate(it)) },
                onEditEvent = { shopId, eventId ->
                    navigator.navigate(
                        Screen.EventEdit(
                            shopId,
                            eventId
                        )
                    )
                }
            )
        }
        entry<Screen.EventCreate> { key ->
            EventFormScreen(
                shopId = key.shopId,
                onBack = navigator.navigateBack,
                onSaved = navigator.navigateBack
            )
        }
        entry<Screen.EventEdit> { key ->
            EventFormScreen(
                shopId = key.shopId,
                eventId = key.eventId,
                onBack = navigator.navigateBack,
                onSaved = navigator.navigateBack
            )
        }
    }
}
