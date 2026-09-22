package `in`.koreatech.business.feature.menu.form

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import `in`.koreatech.business.domain.model.upload.PreSignedUrlDomain
import `in`.koreatech.business.domain.usecase.presignedurl.UploadImageUseCase
import `in`.koreatech.business.domain.usecase.store.GetOwnerMenuCategoriesUseCase
import `in`.koreatech.business.domain.usecase.store.GetOwnerMenuUseCase
import `in`.koreatech.business.domain.usecase.store.SaveOwnerMenuUseCase
import `in`.koreatech.business.feature.menu.form.mapper.toOwnerMenuForm
import `in`.koreatech.business.feature.menu.form.mapper.toOwnerMenuPrices
import `in`.koreatech.business.feature.menu.form.mapper.withCategories
import `in`.koreatech.business.feature.menu.form.mapper.withMenuDetail
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.flow.first
import org.orbitmvi.orbit.OrbitContainerHost
import org.orbitmvi.orbit.blockingIntent
import org.orbitmvi.orbit.viewmodel.orbitContainer

class MenuFormViewModel internal constructor(
    savedStateHandle: SavedStateHandle,
    private val getOwnerMenuCategoriesUseCase: GetOwnerMenuCategoriesUseCase,
    private val getOwnerMenuUseCase: GetOwnerMenuUseCase,
    private val saveOwnerMenuUseCase: SaveOwnerMenuUseCase,
    private val uploadImageUseCase: UploadImageUseCase
) : ViewModel(), OrbitContainerHost<MenuFormState, MenuFormState, MenuFormSideEffect> {
    private val shopId = checkNotNull(savedStateHandle.get<Int>(SHOP_ID_KEY))
    private val menuId = savedStateHandle.get<Int>(MENU_ID_KEY)

    override val container =
        orbitContainer<MenuFormState, MenuFormSideEffect>(
            MenuFormState(shopId = shopId, menuId = menuId),
            onCreate = { load() }
        )

    private suspend fun load() =
        subIntent {
            reduce { state.copy(isLoading = true, error = null) }
            val categories =
                getOwnerMenuCategoriesUseCase(shopId).first().getOrElse {
                    reduce { state.copy(isLoading = false, error = MenuFormError.CategoryLoad) }
                    return@subIntent
                }
            if (menuId == null) {
                reduce { state.withCategories(categories) }
                return@subIntent
            }
            getOwnerMenuUseCase(menuId)
                .first()
                .onSuccess { detail -> reduce { state.withMenuDetail(categories, detail) } }
                .onFailure { reduce { state.copy(isLoading = false, error = MenuFormError.DetailLoad) } }
        }

    fun updateName(value: String) =
        blockingIntent {
            reduce { state.copy(name = value.take(25), error = null) }
        }

    fun updateDescription(value: String) =
        blockingIntent {
            reduce { state.copy(description = value.take(80), error = null) }
        }

    fun toggleCategory(id: Int) =
        blockingIntent {
            val ids = state.selectedCategoryIds.toMutableSet().apply { if (!add(id)) remove(id) }
            reduce { state.copy(selectedCategoryIds = ids.toImmutableSet(), error = null) }
        }

    fun setSinglePrice(single: Boolean) =
        blockingIntent {
            reduce { state.copy(isSinglePrice = single, error = null) }
        }

    fun updateSinglePrice(value: String) =
        blockingIntent {
            reduce { state.copy(singlePrice = value.filter(Char::isDigit), error = null) }
        }

    fun addOption() =
        blockingIntent {
            reduce { state.copy(optionPrices = (state.optionPrices + EditableMenuPrice()).toImmutableList()) }
        }

    fun deleteOption(index: Int) =
        blockingIntent {
            if (state.optionPrices.size > 1) {
                reduce {
                    state.copy(optionPrices = state.optionPrices.filterIndexed { i, _ -> i != index }.toImmutableList())
                }
            }
        }

    fun updateOption(
        index: Int,
        option: String? = null,
        price: String? = null
    ) = blockingIntent {
        reduce {
            state.copy(
                optionPrices =
                state.optionPrices
                    .mapIndexed { i, value ->
                        if (i == index) {
                            value.copy(
                                option = option?.take(50) ?: value.option,
                                price = price?.filter(Char::isDigit) ?: value.price
                            )
                        } else {
                            value
                        }
                    }.toImmutableList(),
                error = null
            )
        }
    }

    fun uploadImage(
        fileName: String,
        contentType: String,
        bytes: ByteArray
    ) = intent {
        if (state.isUploading || state.imageUrls.size >= 3) return@intent
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
            reduce { state.copy(isUploading = false, error = MenuFormError.ImageUpload) }
        }
    }

    fun deleteImage(index: Int) =
        blockingIntent {
            reduce {
                state.copy(imageUrls = state.imageUrls.filterIndexed { i, _ -> i != index }.toImmutableList())
            }
        }

    fun onImageSelectionFailed() =
        blockingIntent {
            reduce { state.copy(error = MenuFormError.ImageUpload) }
        }

    fun save() =
        intent {
            if (state.isSaving || state.isUploading) return@intent
            val prices = state.toOwnerMenuPrices()
            val error =
                when {
                    state.name.isBlank() -> MenuFormError.NameRequired
                    state.selectedCategoryIds.isEmpty() -> MenuFormError.CategoryRequired
                    prices.isNullOrEmpty() -> MenuFormError.PriceInvalid
                    else -> null
                }
            if (error != null) {
                reduce { state.copy(error = error) }
                return@intent
            }
            val validPrices = requireNotNull(prices)
            reduce { state.copy(isSaving = true, error = null) }
            saveOwnerMenuUseCase(
                shopId = shopId,
                menuId = menuId,
                menu = state.toOwnerMenuForm(validPrices)
            ).onSuccess {
                reduce { state.copy(isSaving = false) }
                postSideEffect(MenuFormSideEffect.Saved)
            }.onFailure {
                reduce { state.copy(isSaving = false, error = MenuFormError.Save) }
            }
        }

    companion object {
        internal const val SHOP_ID_KEY = "shopId"
        internal const val MENU_ID_KEY = "menuId"
    }
}
