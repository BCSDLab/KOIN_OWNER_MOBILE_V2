package `in`.koreatech.business.feature.menu

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.domain.usecase.store.CreateOwnerMenuCategoryUseCase
import `in`.koreatech.business.domain.usecase.store.DeleteOwnerMenuCategoryUseCase
import `in`.koreatech.business.domain.usecase.store.DeleteOwnerMenuUseCase
import `in`.koreatech.business.domain.usecase.store.GetOwnerMenusUseCase
import `in`.koreatech.business.domain.usecase.store.ObserveSelectedShopUseCase
import `in`.koreatech.business.domain.usecase.store.UpdateOwnerMenuCategoryUseCase
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.OrbitContainerHost
import org.orbitmvi.orbit.blockingIntent
import org.orbitmvi.orbit.viewmodel.orbitContainer

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class, binding<ViewModel>())
class MenuViewModel(
    private val observeSelectedShopUseCase: ObserveSelectedShopUseCase,
    private val getOwnerMenusUseCase: GetOwnerMenusUseCase,
    private val deleteOwnerMenuUseCase: DeleteOwnerMenuUseCase,
    private val createOwnerMenuCategoryUseCase: CreateOwnerMenuCategoryUseCase,
    private val updateOwnerMenuCategoryUseCase: UpdateOwnerMenuCategoryUseCase,
    private val deleteOwnerMenuCategoryUseCase: DeleteOwnerMenuCategoryUseCase
) : ViewModel(), OrbitContainerHost<MenuState, MenuState, MenuSideEffect> {
    override val container = orbitContainer<MenuState, MenuSideEffect>(MenuState(), onCreate = { observeSelectedShop() })

    private var hasResumed = false

    fun onResume() {
        if (hasResumed) {
            retry()
        } else {
            hasResumed = true
        }
    }

    fun addMenu() = intent {
        reduce { state.copy(isFabMenuExpanded = false) }
        state.shop?.id?.let { postSideEffect(MenuSideEffect.NavigateToCreate(it)) }
    }

    fun toggleFabMenu() = blockingIntent {
        reduce { state.copy(isFabMenuExpanded = !state.isFabMenuExpanded) }
    }

    fun openCreateCategory() = blockingIntent {
        reduce {
            state.copy(
                isFabMenuExpanded = false,
                isCategoryEditorVisible = true,
                categoryEditorId = null,
                categoryName = ""
            )
        }
    }

    fun openEditCategory(
        categoryId: Int,
        categoryName: String
    ) = blockingIntent {
        reduce {
            state.copy(
                isCategoryEditorVisible = true,
                categoryEditorId = categoryId,
                categoryName = categoryName
            )
        }
    }

    fun updateCategoryName(value: String) = blockingIntent {
        reduce { state.copy(categoryName = value.take(20)) }
    }

    fun dismissCategoryEditor() = blockingIntent {
        if (!state.isSavingCategory) {
            reduce {
                state.copy(
                    isCategoryEditorVisible = false,
                    categoryEditorId = null,
                    categoryName = ""
                )
            }
        }
    }

    fun saveCategory() = intent {
        val shopId = state.shop?.id ?: return@intent
        val name = state.categoryName.trim()
        if (name.isEmpty() || state.isSavingCategory) return@intent
        reduce { state.copy(isSavingCategory = true) }
        val result = state.categoryEditorId?.let {
            updateOwnerMenuCategoryUseCase(it, name)
        } ?: createOwnerMenuCategoryUseCase(shopId, name)
        if (result.isFailure) {
            reduce { state.copy(isSavingCategory = false) }
            postSideEffect(MenuSideEffect.ShowError(MenuError.CategorySave))
            return@intent
        }
        reloadAfterCategorySave(shopId)
    }

    fun requestDeleteCategory(
        categoryId: Int,
        categoryName: String
    ) = blockingIntent {
        reduce {
            state.copy(
                deleteCategoryId = categoryId,
                deleteCategoryName = categoryName
            )
        }
    }

    fun dismissDeleteCategory() = blockingIntent {
        if (!state.isDeletingCategory) {
            reduce { state.copy(deleteCategoryId = null, deleteCategoryName = null) }
        }
    }

    fun deleteCategory() = intent {
        val shopId = state.shop?.id ?: return@intent
        val categoryId = state.deleteCategoryId ?: return@intent
        if (state.isDeletingCategory) return@intent
        reduce { state.copy(isDeletingCategory = true) }
        if (deleteOwnerMenuCategoryUseCase(categoryId).isFailure) {
            reduce { state.copy(isDeletingCategory = false) }
            postSideEffect(MenuSideEffect.ShowError(MenuError.CategoryDelete))
            return@intent
        }
        reloadAfterCategoryDelete(shopId)
    }

    fun editMenu(menuId: Int) = intent {
        state.shop?.id?.let { postSideEffect(MenuSideEffect.NavigateToEdit(it, menuId)) }
    }

    fun requestDelete(
        menuId: Int,
        menuName: String
    ) = blockingIntent {
        reduce { state.copy(deleteMenuId = menuId, deleteMenuName = menuName) }
    }

    fun dismissDelete() = blockingIntent {
        if (!state.isDeleting) reduce { state.copy(deleteMenuId = null, deleteMenuName = null) }
    }

    fun deleteMenu() = intent {
        val menuId = state.deleteMenuId ?: return@intent
        val shopId = state.shop?.id ?: return@intent
        if (state.isDeleting) return@intent
        reduce { state.copy(isDeleting = true) }
        if (deleteOwnerMenuUseCase(menuId).isFailure) {
            reduce { state.copy(isDeleting = false) }
            postSideEffect(MenuSideEffect.ShowError(MenuError.MenuDelete))
            return@intent
        }
        reloadAfterMenuDelete(shopId)
    }

    private suspend fun reloadAfterCategorySave(shopId: Int) = subIntent {
        getOwnerMenusUseCase(shopId)
            .first()
            .onSuccess {
                reduce {
                    state.copy(
                        categories = it.toImmutableList(),
                        isCategoryEditorVisible = false,
                        categoryEditorId = null,
                        categoryName = "",
                        isSavingCategory = false
                    )
                }
            }.onFailure {
                reduce { state.copy(isSavingCategory = false) }
                postSideEffect(MenuSideEffect.ShowError(MenuError.MenuReload))
            }
    }

    private suspend fun reloadAfterCategoryDelete(shopId: Int) = subIntent {
        getOwnerMenusUseCase(shopId)
            .first()
            .onSuccess {
                reduce {
                    state.copy(
                        categories = it.toImmutableList(),
                        deleteCategoryId = null,
                        deleteCategoryName = null,
                        isDeletingCategory = false
                    )
                }
            }.onFailure {
                reduce { state.copy(isDeletingCategory = false) }
                postSideEffect(MenuSideEffect.ShowError(MenuError.MenuReload))
            }
    }

    private suspend fun reloadAfterMenuDelete(shopId: Int) = subIntent {
        getOwnerMenusUseCase(shopId)
            .first()
            .onSuccess { menus ->
                reduce {
                    state.copy(
                        categories = menus.toImmutableList(),
                        isDeleting = false,
                        deleteMenuId = null,
                        deleteMenuName = null
                    )
                }
            }.onFailure {
                reduce { state.copy(isDeleting = false, deleteMenuId = null, deleteMenuName = null) }
                postSideEffect(MenuSideEffect.ShowError(MenuError.MenuReload))
            }
    }

    fun retry() = intent {
        state.shop?.id?.let { shopId ->
            getOwnerMenusUseCase(shopId)
                .onStart { reduce { state.copy(isLoading = true) } }
                .onEach { result ->
                    result
                        .onSuccess {
                            reduce { state.copy(categories = it.toImmutableList(), isLoading = false) }
                        }.onFailure {
                            reduce { state.copy(isLoading = false) }
                            postSideEffect(MenuSideEffect.ShowError(MenuError.MenuLoad))
                        }
                }.collect()
        }
    }

    fun refresh() = intent {
        val shopId = state.shop?.id ?: return@intent
        if (state.isRefreshing) return@intent
        getOwnerMenusUseCase(shopId)
            .onStart { reduce { state.copy(isRefreshing = true) } }
            .onEach { result ->
                result
                    .onSuccess {
                        reduce { state.copy(categories = it.toImmutableList(), isRefreshing = false) }
                    }.onFailure {
                        reduce { state.copy(isRefreshing = false) }
                        postSideEffect(MenuSideEffect.ShowError(MenuError.MenuReload))
                    }
            }.collect()
    }

    private suspend fun observeSelectedShop() = subIntent {
        observeSelectedShopUseCase().collectLatest { result ->
            result
                .onSuccess { shop ->
                    reduce { state.copy(shop = shop, categories = persistentListOf()) }
                    shop?.id?.let { shopId ->
                        getOwnerMenusUseCase(shopId)
                            .onStart { reduce { state.copy(isLoading = true) } }
                            .onEach { result ->
                                result
                                    .onSuccess { menus ->
                                        reduce {
                                            state.copy(
                                                categories = menus.toImmutableList(),
                                                isLoading = false
                                            )
                                        }
                                    }.onFailure {
                                        reduce { state.copy(isLoading = false) }
                                        postSideEffect(MenuSideEffect.ShowError(MenuError.MenuLoad))
                                    }
                            }.collect()
                    }
                }.onFailure {
                    postSideEffect(MenuSideEffect.ShowError(MenuError.ShopLoad))
                }
        }
    }
}
