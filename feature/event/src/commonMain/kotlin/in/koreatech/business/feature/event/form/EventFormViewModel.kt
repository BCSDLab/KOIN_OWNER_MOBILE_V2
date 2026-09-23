package `in`.koreatech.business.feature.event.form

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import `in`.koreatech.business.domain.model.upload.PreSignedUrlDomain
import `in`.koreatech.business.domain.usecase.presignedurl.UploadImageUseCase
import `in`.koreatech.business.domain.usecase.store.CreateOwnerEventUseCase
import `in`.koreatech.business.domain.usecase.store.GetOwnerEventsUseCase
import `in`.koreatech.business.domain.usecase.store.UpdateOwnerEventUseCase
import `in`.koreatech.business.feature.event.form.mapper.toOwnerEventForm
import `in`.koreatech.business.feature.event.form.mapper.withOwnerEvent
import `in`.koreatech.business.feature.event.util.dateToEpochMillis
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.OrbitContainerHost
import org.orbitmvi.orbit.blockingIntent
import org.orbitmvi.orbit.viewmodel.orbitContainer

class EventFormViewModel internal constructor(
    savedStateHandle: SavedStateHandle,
    private val createOwnerEventUseCase: CreateOwnerEventUseCase,
    private val updateOwnerEventUseCase: UpdateOwnerEventUseCase,
    private val getOwnerEventsUseCase: GetOwnerEventsUseCase,
    private val uploadImageUseCase: UploadImageUseCase
) : ViewModel(), OrbitContainerHost<EventFormState, EventFormState, EventFormSideEffect> {
    private val shopId = checkNotNull(savedStateHandle.get<Int>(SHOP_ID_KEY))
    private val eventId = savedStateHandle.get<Int>(EVENT_ID_KEY)

    override val container = orbitContainer<EventFormState, EventFormSideEffect>(
        initialState = EventFormState(isEdit = eventId != null, isLoading = eventId != null),
        onCreate = { loadEvent() }
    )

    private suspend fun loadEvent() = subIntent {
        val id = eventId ?: return@subIntent
        getOwnerEventsUseCase(shopId)
            .onStart { reduce { state.copy(isLoading = true, error = null) } }
            .onEach { result ->
                val events = result.getOrElse {
                    reduce { state.copy(isLoading = false, error = EventFormError.Load) }
                    return@onEach
                }
                val event = events.firstOrNull { it.id == id }
                if (event == null) {
                    reduce { state.copy(isLoading = false, error = EventFormError.Load) }
                    return@onEach
                }
                reduce { state.withOwnerEvent(event) }
            }.collect()
    }

    fun updateTitle(value: String) = blockingIntent {
        reduce { state.copy(title = value.take(MAX_TITLE_LENGTH), error = null) }
    }

    fun updateContent(value: String) = blockingIntent {
        reduce { state.copy(content = value.take(MAX_CONTENT_LENGTH), error = null) }
    }

    fun updateStartDate(value: String) = blockingIntent {
        reduce {
            state.copy(
                startDate = value,
                endDate = state.endDate.takeUnless { it.isNotEmpty() && it < value }.orEmpty(),
                error = null
            )
        }
    }

    fun updateEndDate(value: String) = blockingIntent {
        reduce { state.copy(endDate = value, error = null) }
    }

    fun uploadImage(
        fileName: String,
        contentType: String,
        bytes: ByteArray
    ) = intent {
        if (state.isUploading || state.imageUrls.size >= MAX_IMAGE_COUNT) return@intent
        reduce { state.copy(isUploading = true, error = null) }
        uploadImageUseCase(
            domain = PreSignedUrlDomain.MARKET,
            contentLength = bytes.size.toLong(),
            contentType = contentType,
            fileName = fileName,
            bytes = bytes
        ).onSuccess { url ->
            reduce {
                state.copy(
                    imageUrls = (state.imageUrls + url).toImmutableList(),
                    isUploading = false
                )
            }
        }.onFailure {
            reduce { state.copy(isUploading = false, error = EventFormError.ImageUpload) }
        }
    }

    fun deleteImage(index: Int) = blockingIntent {
        reduce {
            state.copy(
                imageUrls = state.imageUrls
                    .filterIndexed { imageIndex, _ -> imageIndex != index }
                    .toImmutableList()
            )
        }
    }

    fun onImageSelectionFailed() = blockingIntent {
        reduce { state.copy(error = EventFormError.ImageUpload) }
    }

    fun save() = intent {
        if (state.isSaving || state.isUploading) return@intent
        val error = when {
            state.title.isBlank() -> EventFormError.TitleRequired
            state.content.isBlank() -> EventFormError.ContentRequired
            dateToEpochMillis(state.startDate) == null ||
                dateToEpochMillis(state.endDate) == null ||
                state.startDate > state.endDate -> EventFormError.DateInvalid
            else -> null
        }
        if (error != null) {
            reduce { state.copy(error = error) }
            return@intent
        }
        val event = state.toOwnerEventForm()
        reduce { state.copy(isSaving = true, error = null) }
        val result = eventId?.let {
            updateOwnerEventUseCase(shopId, it, event)
        } ?: createOwnerEventUseCase(shopId, event)
        result
            .onSuccess {
                reduce { state.copy(isSaving = false) }
                postSideEffect(EventFormSideEffect.Saved)
            }.onFailure {
                reduce { state.copy(isSaving = false, error = EventFormError.Save) }
            }
    }

    companion object {
        internal const val SHOP_ID_KEY = "shopId"
        internal const val EVENT_ID_KEY = "eventId"
        private const val MAX_TITLE_LENGTH = 25
        private const val MAX_CONTENT_LENGTH = 500
        private const val MAX_IMAGE_COUNT = 3
    }
}
