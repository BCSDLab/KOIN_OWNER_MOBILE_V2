package `in`.koreatech.business.feature.event

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.domain.model.store.OwnerEvent
import `in`.koreatech.business.domain.usecase.store.DeleteOwnerEventUseCase
import `in`.koreatech.business.domain.usecase.store.GetOwnerEventsUseCase
import `in`.koreatech.business.domain.usecase.store.ObserveSelectedShopUseCase
import `in`.koreatech.business.feature.event.model.EventShopUiModel
import `in`.koreatech.business.feature.event.model.toEventUiModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.OrbitContainerHost
import org.orbitmvi.orbit.blockingIntent
import org.orbitmvi.orbit.viewmodel.orbitContainer

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class, binding<ViewModel>())
class EventViewModel(
    private val observeSelectedShopUseCase: ObserveSelectedShopUseCase,
    private val getOwnerEventsUseCase: GetOwnerEventsUseCase,
    private val deleteOwnerEventUseCase: DeleteOwnerEventUseCase
) : ViewModel(), OrbitContainerHost<EventState, EventState, EventSideEffect> {
    override val container = orbitContainer<EventState, EventSideEffect>(EventState(), onCreate = { observeSelectedShop() })

    private var hasResumed = false

    fun onResume() {
        if (hasResumed) {
            retry()
        } else {
            hasResumed = true
        }
    }

    fun retry() = intent {
        state.shop?.id?.let { shopId ->
            getOwnerEventsUseCase(shopId)
                .onStart { reduce { state.copy(isLoading = true) } }
                .onEach { result ->
                    result
                        .onSuccess {
                            reduce { state.copy(events = it.map(OwnerEvent::toEventUiModel).toImmutableList(), isLoading = false) }
                        }.onFailure {
                            reduce { state.copy(isLoading = false) }
                            postSideEffect(EventSideEffect.EventLoadFailed)
                        }
                }.collect()
        }
    }

    fun addEvent() = intent {
        state.shop?.id?.let { postSideEffect(EventSideEffect.NavigateToCreate(it)) }
    }

    fun editEvent(eventId: Int) = intent {
        state.shop?.id?.let { postSideEffect(EventSideEffect.NavigateToEdit(it, eventId)) }
    }

    fun requestDelete(
        eventId: Int,
        eventTitle: String
    ) = blockingIntent {
        reduce {
            state.copy(
                deleteEventId = eventId,
                deleteEventTitle = eventTitle
            )
        }
    }

    fun dismissDelete() = blockingIntent {
        if (!state.isDeleting) {
            reduce { state.copy(deleteEventId = null, deleteEventTitle = null) }
        }
    }

    fun deleteEvent() = intent {
        val shopId = state.shop?.id ?: return@intent
        val eventId = state.deleteEventId ?: return@intent
        if (state.isDeleting) return@intent
        reduce { state.copy(isDeleting = true) }
        deleteOwnerEventUseCase(shopId, eventId)
            .onSuccess {
                reduce {
                    state.copy(
                        events = state.events.filterNot { it.id == eventId }.toImmutableList(),
                        isDeleting = false,
                        deleteEventId = null,
                        deleteEventTitle = null
                    )
                }
            }.onFailure {
                reduce { state.copy(isDeleting = false) }
                postSideEffect(EventSideEffect.EventDeleteFailed)
            }
    }

    fun refresh() = intent {
        val shopId = state.shop?.id ?: return@intent
        if (state.isRefreshing) return@intent
        getOwnerEventsUseCase(shopId)
            .onStart { reduce { state.copy(isRefreshing = true) } }
            .onEach { result ->
                result
                    .onSuccess {
                        reduce { state.copy(events = it.map(OwnerEvent::toEventUiModel).toImmutableList(), isRefreshing = false) }
                    }.onFailure {
                        reduce { state.copy(isRefreshing = false) }
                        postSideEffect(EventSideEffect.EventReloadFailed)
                    }
            }.collect()
    }

    private suspend fun observeSelectedShop() = subIntent {
        observeSelectedShopUseCase().collectLatest { result ->
            result
                .onSuccess { shop ->
                    reduce {
                        state.copy(
                            shop = shop?.let {
                                EventShopUiModel(
                                    id = it.id,
                                    name = it.name
                                )
                            },
                            events = persistentListOf()
                        )
                    }
                    shop?.id?.let { shopId ->
                        getOwnerEventsUseCase(shopId)
                            .onStart { reduce { state.copy(isLoading = true) } }
                            .onEach { result ->
                                result
                                    .onSuccess { events ->
                                        reduce {
                                            state.copy(
                                                events = events.map(OwnerEvent::toEventUiModel).toImmutableList(),
                                                isLoading = false
                                            )
                                        }
                                    }.onFailure {
                                        reduce { state.copy(isLoading = false) }
                                        postSideEffect(EventSideEffect.EventLoadFailed)
                                    }
                            }.collect()
                    }
                }.onFailure {
                    postSideEffect(EventSideEffect.ShopLoadFailed)
                }
        }
    }
}
