package `in`.koreatech.business.feature.menu.form

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactory
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactoryKey
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.domain.usecase.presignedurl.UploadImageUseCase
import `in`.koreatech.business.domain.usecase.store.GetOwnerMenuCategoriesUseCase
import `in`.koreatech.business.domain.usecase.store.GetOwnerMenuUseCase
import `in`.koreatech.business.domain.usecase.store.SaveOwnerMenuUseCase

@Inject
@ViewModelAssistedFactoryKey(MenuFormViewModel::class)
@ContributesIntoMap(AppScope::class)
class MenuFormViewModelFactory(
    private val getOwnerMenuCategoriesUseCase: GetOwnerMenuCategoriesUseCase,
    private val getOwnerMenuUseCase: GetOwnerMenuUseCase,
    private val saveOwnerMenuUseCase: SaveOwnerMenuUseCase,
    private val uploadImageUseCase: UploadImageUseCase
) : ViewModelAssistedFactory {
    override fun create(extras: CreationExtras) = MenuFormViewModel(
        savedStateHandle = extras.createSavedStateHandle(),
        getOwnerMenuCategoriesUseCase = getOwnerMenuCategoriesUseCase,
        getOwnerMenuUseCase = getOwnerMenuUseCase,
        saveOwnerMenuUseCase = saveOwnerMenuUseCase,
        uploadImageUseCase = uploadImageUseCase
    )
}
