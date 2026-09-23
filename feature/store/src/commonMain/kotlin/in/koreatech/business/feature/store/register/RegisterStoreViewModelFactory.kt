package `in`.koreatech.business.feature.store.register

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactory
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactoryKey
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.domain.usecase.address.SearchAddressUseCase
import `in`.koreatech.business.domain.usecase.presignedurl.UploadImageUseCase
import `in`.koreatech.business.domain.usecase.store.GetOwnerShopUseCase
import `in`.koreatech.business.domain.usecase.store.GetShopCategoriesUseCase
import `in`.koreatech.business.domain.usecase.store.SaveOwnerShopUseCase

@Inject
@ViewModelAssistedFactoryKey(RegisterStoreViewModel::class)
@ContributesIntoMap(AppScope::class)
class RegisterStoreViewModelFactory(
    private val getShopCategoriesUseCase: GetShopCategoriesUseCase,
    private val getOwnerShopUseCase: GetOwnerShopUseCase,
    private val saveOwnerShopUseCase: SaveOwnerShopUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val searchAddressUseCase: SearchAddressUseCase
) : ViewModelAssistedFactory {
    override fun create(extras: CreationExtras) = RegisterStoreViewModel(
        savedStateHandle = extras.createSavedStateHandle(),
        getShopCategoriesUseCase = getShopCategoriesUseCase,
        getOwnerShopUseCase = getOwnerShopUseCase,
        saveOwnerShopUseCase = saveOwnerShopUseCase,
        uploadImageUseCase = uploadImageUseCase,
        searchAddressUseCase = searchAddressUseCase
    )
}
