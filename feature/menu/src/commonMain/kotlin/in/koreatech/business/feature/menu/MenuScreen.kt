package `in`.koreatech.business.feature.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import dev.zacsweers.metrox.viewmodel.metroViewModel
import `in`.koreatech.business.core.designsystem.component.KoinEmptyContent
import `in`.koreatech.business.core.designsystem.component.KoinImageThumbnail
import `in`.koreatech.business.core.designsystem.component.KoinLoadingContent
import `in`.koreatech.business.core.designsystem.component.KoinScreenTitle
import `in`.koreatech.business.core.designsystem.component.KoinSelectableChipGroup
import `in`.koreatech.business.core.designsystem.component.KoinSelectableItem
import `in`.koreatech.business.core.designsystem.component.KoinUnderlineTextField
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_add_symbol
import `in`.koreatech.business.core.designsystem.generated.resources.common_cancel
import `in`.koreatech.business.core.designsystem.generated.resources.common_confirm
import `in`.koreatech.business.core.designsystem.generated.resources.common_delete
import `in`.koreatech.business.core.designsystem.generated.resources.common_edit
import `in`.koreatech.business.core.designsystem.generated.resources.common_remove_symbol
import `in`.koreatech.business.core.designsystem.generated.resources.common_won
import `in`.koreatech.business.core.designsystem.generated.resources.error_menu_category_delete
import `in`.koreatech.business.core.designsystem.generated.resources.error_menu_category_save
import `in`.koreatech.business.core.designsystem.generated.resources.error_menu_delete
import `in`.koreatech.business.core.designsystem.generated.resources.error_menu_load
import `in`.koreatech.business.core.designsystem.generated.resources.error_menu_reload
import `in`.koreatech.business.core.designsystem.generated.resources.error_shop_load
import `in`.koreatech.business.core.designsystem.generated.resources.menu_add_title
import `in`.koreatech.business.core.designsystem.generated.resources.menu_category_add
import `in`.koreatech.business.core.designsystem.generated.resources.menu_category_create_title
import `in`.koreatech.business.core.designsystem.generated.resources.menu_category_delete_description
import `in`.koreatech.business.core.designsystem.generated.resources.menu_category_delete_title
import `in`.koreatech.business.core.designsystem.generated.resources.menu_category_edit_title
import `in`.koreatech.business.core.designsystem.generated.resources.menu_category_name
import `in`.koreatech.business.core.designsystem.generated.resources.menu_category_name_hint
import `in`.koreatech.business.core.designsystem.generated.resources.menu_category_saving
import `in`.koreatech.business.core.designsystem.generated.resources.menu_delete_description
import `in`.koreatech.business.core.designsystem.generated.resources.menu_delete_title
import `in`.koreatech.business.core.designsystem.generated.resources.menu_deleting
import `in`.koreatech.business.core.designsystem.generated.resources.menu_empty
import `in`.koreatech.business.core.designsystem.generated.resources.menu_hidden
import `in`.koreatech.business.core.designsystem.generated.resources.menu_image_description
import `in`.koreatech.business.core.designsystem.generated.resources.menu_price_not_registered
import `in`.koreatech.business.core.designsystem.generated.resources.menu_shop_required
import `in`.koreatech.business.core.designsystem.generated.resources.menu_title
import `in`.koreatech.business.core.designsystem.noRippleClickable
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.domain.model.store.OwnerMenu
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun MenuScreen(
    onCreateMenu: (Int) -> Unit,
    onEditMenu: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MenuViewModel = metroViewModel()
) {
    val state by viewModel.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val errorMessages =
        mapOf(
            MenuError.ShopLoad to stringResource(Res.string.error_shop_load),
            MenuError.MenuLoad to stringResource(Res.string.error_menu_load),
            MenuError.MenuReload to stringResource(Res.string.error_menu_reload),
            MenuError.MenuDelete to stringResource(Res.string.error_menu_delete),
            MenuError.CategorySave to stringResource(Res.string.error_menu_category_save),
            MenuError.CategoryDelete to stringResource(Res.string.error_menu_category_delete)
        )
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.onResume()
    }
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is MenuSideEffect.NavigateToCreate -> onCreateMenu(sideEffect.shopId)
            is MenuSideEffect.NavigateToEdit -> onEditMenu(sideEffect.shopId, sideEffect.menuId)
            is MenuSideEffect.ShowError ->
                snackbarHostState.showSnackbar(
                    errorMessages.getValue(sideEffect.error)
                )
        }
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = KoinTheme.colors.neutral75,
        topBar = {
            Column {
                KoinScreenTitle(
                    text = stringResource(Res.string.menu_title),
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)
                )
                state.shop?.let { shop ->
                    Text(
                        text = shop.name,
                        modifier = Modifier.padding(horizontal = 24.dp),
                        style = KoinTheme.typography.medium14,
                        color = KoinTheme.colors.neutral600
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            if (state.shop != null) {
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (state.isFabMenuExpanded) {
                        ExtendedFloatingActionButton(
                            onClick = viewModel::addMenu,
                            containerColor = Color.White,
                            contentColor = KoinTheme.colors.neutral800
                        ) {
                            Text(
                                text = stringResource(Res.string.menu_add_title),
                                style = KoinTheme.typography.medium14
                            )
                        }
                        ExtendedFloatingActionButton(
                            onClick = viewModel::openCreateCategory,
                            containerColor = Color.White,
                            contentColor = KoinTheme.colors.neutral800
                        ) {
                            Text(
                                text = stringResource(Res.string.menu_category_add),
                                style = KoinTheme.typography.medium14
                            )
                        }
                    }
                    FloatingActionButton(
                        onClick = viewModel::toggleFabMenu,
                        shape = CircleShape,
                        containerColor = KoinTheme.colors.primary500,
                        contentColor = Color.White,
                        elevation =
                        FloatingActionButtonDefaults.elevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 0.dp,
                            focusedElevation = 0.dp,
                            hoveredElevation = 0.dp
                        )
                    ) {
                        Text(
                            text =
                            stringResource(
                                if (state.isFabMenuExpanded) {
                                    Res.string.common_remove_symbol
                                } else {
                                    Res.string.common_add_symbol
                                }
                            ),
                            style = KoinTheme.typography.bold20,
                            color = Color.White
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        MenuScreenImpl(
            state = state,
            onEditCategory = viewModel::openEditCategory,
            onDeleteCategory = viewModel::requestDeleteCategory,
            onEditMenu = viewModel::editMenu,
            onDeleteMenu = viewModel::requestDelete,
            onConfirmDelete = viewModel::deleteMenu,
            onDismissDelete = viewModel::dismissDelete,
            onRefresh = viewModel::refresh,
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        )
        MenuCategoryDialogs(
            state = state,
            onNameChange = viewModel::updateCategoryName,
            onSave = viewModel::saveCategory,
            onDismissEditor = viewModel::dismissCategoryEditor,
            onConfirmDelete = viewModel::deleteCategory,
            onDismissDelete = viewModel::dismissDeleteCategory
        )
    }
}

@Composable
fun MenuScreenImpl(
    state: MenuState,
    onEditCategory: (Int, String) -> Unit,
    onDeleteCategory: (Int, String) -> Unit,
    onEditMenu: (Int) -> Unit,
    onDeleteMenu: (Int, String) -> Unit,
    onConfirmDelete: () -> Unit,
    onDismissDelete: () -> Unit,
    onRefresh: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val categoryStartIndices =
        remember(state.categories) {
            buildMap {
                var itemIndex = 0
                state.categories.forEach { category ->
                    put(category.id, itemIndex)
                    itemIndex += category.menus.size + 1
                }
            }
        }
    val selectedCategoryId by remember(state.categories, categoryStartIndices, listState) {
        derivedStateOf {
            categoryStartIndices.entries
                .lastOrNull { it.value <= listState.firstVisibleItemIndex }
                ?.key
                ?: state.categories.firstOrNull()?.id
        }
    }
    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier.padding(contentPadding)
    ) {
        when {
            state.isLoading -> KoinLoadingContent(Modifier.fillMaxSize())
            state.shop == null ->
                KoinEmptyContent(
                    message = stringResource(Res.string.menu_shop_required),
                    modifier = Modifier.fillMaxSize()
                )

            state.categories.isEmpty() ->
                KoinEmptyContent(
                    message = stringResource(Res.string.menu_empty),
                    modifier = Modifier.fillMaxSize()
                )

            else ->
                Column(modifier = Modifier.fillMaxSize()) {
                    KoinSelectableChipGroup(
                        items = state.categories.map { KoinSelectableItem(it.id, it.name) },
                        selectedItemId = selectedCategoryId,
                        onItemClick = { categoryId ->
                            categoryStartIndices[categoryId]?.let { itemIndex ->
                                coroutineScope.launch { listState.animateScrollToItem(itemIndex) }
                            }
                        },
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                    )
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
                        contentPadding = PaddingValues(bottom = 96.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        state.categories.forEach { category ->
                            item(key = "category-${category.id}") {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = category.name,
                                        style = KoinTheme.typography.bold18,
                                        color = KoinTheme.colors.neutral800,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = stringResource(Res.string.common_edit),
                                        style = KoinTheme.typography.medium13,
                                        color = KoinTheme.colors.primary500,
                                        modifier =
                                        Modifier.noRippleClickable {
                                            onEditCategory(category.id, category.name)
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = stringResource(Res.string.common_delete),
                                        style = KoinTheme.typography.medium13,
                                        color = KoinTheme.colors.danger600,
                                        modifier =
                                        Modifier.noRippleClickable {
                                            onDeleteCategory(category.id, category.name)
                                        }
                                    )
                                }
                            }
                            items(category.menus, key = { "${category.id}-${it.id}" }) { menu ->
                                MenuCard(
                                    menu = menu,
                                    onEdit = { onEditMenu(menu.id) },
                                    onDelete = { onDeleteMenu(menu.id, menu.name) }
                                )
                            }
                        }
                        item(key = "category-scroll-spacer") {
                            Spacer(modifier = Modifier.fillParentMaxSize(0.7f))
                        }
                    }
                }
        }
    }
    if (state.deleteMenuId != null) {
        AlertDialog(
            onDismissRequest = onDismissDelete,
            title = {
                Text(
                    text = stringResource(Res.string.menu_delete_title),
                    style = KoinTheme.typography.bold18
                )
            },
            text = {
                Text(
                    text =
                    stringResource(
                        Res.string.menu_delete_description,
                        state.deleteMenuName.orEmpty()
                    ),
                    style = KoinTheme.typography.regular14
                )
            },
            confirmButton = {
                TextButton(enabled = !state.isDeleting, onClick = onConfirmDelete) {
                    Text(
                        text =
                        stringResource(
                            if (state.isDeleting) Res.string.menu_deleting else Res.string.common_delete
                        ),
                        color = KoinTheme.colors.danger600
                    )
                }
            },
            dismissButton = {
                TextButton(enabled = !state.isDeleting, onClick = onDismissDelete) {
                    Text(stringResource(Res.string.common_cancel))
                }
            }
        )
    }
}

@Composable
private fun MenuCategoryDialogs(
    state: MenuState,
    onNameChange: (String) -> Unit,
    onSave: () -> Unit,
    onDismissEditor: () -> Unit,
    onConfirmDelete: () -> Unit,
    onDismissDelete: () -> Unit
) {
    if (state.isCategoryEditorVisible) {
        AlertDialog(
            onDismissRequest = onDismissEditor,
            title = {
                Text(
                    text =
                    stringResource(
                        if (state.categoryEditorId == null) {
                            Res.string.menu_category_create_title
                        } else {
                            Res.string.menu_category_edit_title
                        }
                    ),
                    style = KoinTheme.typography.bold18
                )
            },
            text = {
                KoinUnderlineTextField(
                    title = { Text(stringResource(Res.string.menu_category_name), style = KoinTheme.typography.medium15) },
                    value = state.categoryName,
                    onValueChange = onNameChange,
                    placeholder = stringResource(Res.string.menu_category_name_hint)
                )
            },
            confirmButton = {
                TextButton(
                    enabled = state.categoryName.isNotBlank() && !state.isSavingCategory,
                    onClick = onSave
                ) {
                    Text(
                        text =
                        stringResource(
                            if (state.isSavingCategory) {
                                Res.string.menu_category_saving
                            } else {
                                Res.string.common_confirm
                            }
                        )
                    )
                }
            },
            dismissButton = {
                TextButton(enabled = !state.isSavingCategory, onClick = onDismissEditor) {
                    Text(stringResource(Res.string.common_cancel))
                }
            }
        )
    }
    if (state.deleteCategoryId != null) {
        AlertDialog(
            onDismissRequest = onDismissDelete,
            title = {
                Text(
                    text = stringResource(Res.string.menu_category_delete_title),
                    style = KoinTheme.typography.bold18
                )
            },
            text = {
                Text(
                    text =
                    stringResource(
                        Res.string.menu_category_delete_description,
                        state.deleteCategoryName.orEmpty()
                    ),
                    style = KoinTheme.typography.regular14
                )
            },
            confirmButton = {
                TextButton(
                    enabled = !state.isDeletingCategory,
                    onClick = onConfirmDelete
                ) {
                    Text(
                        text = stringResource(Res.string.common_delete),
                        color = KoinTheme.colors.danger600
                    )
                }
            },
            dismissButton = {
                TextButton(enabled = !state.isDeletingCategory, onClick = onDismissDelete) {
                    Text(stringResource(Res.string.common_cancel))
                }
            }
        )
    }
}

@Composable
private fun MenuCard(
    menu: OwnerMenu,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val won = stringResource(Res.string.common_won)
    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(
                width = 0.5.dp,
                color = KoinTheme.colors.neutral300,
                shape = RoundedCornerShape(12.dp)
            ).padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        KoinImageThumbnail(
            imageUrl = menu.imageUrls.firstOrNull(),
            contentDescription = stringResource(Res.string.menu_image_description, 1),
            modifier = Modifier.size(88.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = menu.name,
                    style = KoinTheme.typography.medium16,
                    color = KoinTheme.colors.neutral800,
                    modifier = Modifier.weight(1f)
                )
                if (menu.isHidden) {
                    Text(
                        text = stringResource(Res.string.menu_hidden),
                        style = KoinTheme.typography.medium12,
                        color = KoinTheme.colors.danger600
                    )
                }
            }
            menu.description?.takeIf(String::isNotBlank)?.let {
                Text(
                    text = it,
                    style = KoinTheme.typography.regular13,
                    color = KoinTheme.colors.neutral500
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text =
                menu.prices
                    .joinToString(" · ") { price ->
                        listOfNotNull(
                            price.option,
                            "${price.price}$won"
                        ).joinToString(" ")
                    }.ifBlank { stringResource(Res.string.menu_price_not_registered) },
                style = KoinTheme.typography.medium14,
                color = KoinTheme.colors.neutral700
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(Res.string.common_edit),
                    style = KoinTheme.typography.medium13,
                    color = KoinTheme.colors.primary500,
                    modifier = Modifier.noRippleClickable(onClick = onEdit)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = stringResource(Res.string.common_delete),
                    style = KoinTheme.typography.medium13,
                    color = KoinTheme.colors.danger600,
                    modifier = Modifier.noRippleClickable(onClick = onDelete)
                )
            }
        }
    }
}

@Preview
@Composable
private fun MenuScreenPreview() {
    KoinTheme {
        MenuScreenImpl(
            state = MenuState(),
            onEditCategory = { _, _ -> },
            onDeleteCategory = { _, _ -> },
            onEditMenu = {},
            onDeleteMenu = { _, _ -> },
            onConfirmDelete = {},
            onDismissDelete = {},
            onRefresh = {},
            contentPadding = PaddingValues()
        )
    }
}
