package `in`.koreatech.business.feature.event.form

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactory
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactoryKey
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.domain.usecase.presignedurl.UploadImageUseCase
import `in`.koreatech.business.domain.usecase.store.CreateOwnerEventUseCase
import `in`.koreatech.business.domain.usecase.store.GetOwnerEventsUseCase
import `in`.koreatech.business.domain.usecase.store.UpdateOwnerEventUseCase

@Inject
@ViewModelAssistedFactoryKey(EventFormViewModel::class)
@ContributesIntoMap(AppScope::class)
class EventFormViewModelFactory(
    private val createOwnerEventUseCase: CreateOwnerEventUseCase,
    private val updateOwnerEventUseCase: UpdateOwnerEventUseCase,
    private val getOwnerEventsUseCase: GetOwnerEventsUseCase,
    private val uploadImageUseCase: UploadImageUseCase
) : ViewModelAssistedFactory {
    override fun create(extras: CreationExtras) = EventFormViewModel(
        savedStateHandle = extras.createSavedStateHandle(),
        createOwnerEventUseCase = createOwnerEventUseCase,
        updateOwnerEventUseCase = updateOwnerEventUseCase,
        getOwnerEventsUseCase = getOwnerEventsUseCase,
        uploadImageUseCase = uploadImageUseCase
    )
}
