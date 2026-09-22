package `in`.koreatech.business.feature.store.manage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import dev.zacsweers.metrox.viewmodel.metroViewModel
import `in`.koreatech.business.core.designsystem.component.KoinEmptyContent
import `in`.koreatech.business.core.designsystem.component.KoinLoadingContent
import `in`.koreatech.business.core.designsystem.component.topbar.KoinTopAppBar
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_edit
import `in`.koreatech.business.core.designsystem.generated.resources.error_shop_load
import `in`.koreatech.business.core.designsystem.generated.resources.manage_shops_address_not_registered
import `in`.koreatech.business.core.designsystem.generated.resources.manage_shops_current
import `in`.koreatech.business.core.designsystem.generated.resources.manage_shops_empty
import `in`.koreatech.business.core.designsystem.generated.resources.manage_shops_register
import `in`.koreatech.business.core.designsystem.generated.resources.manage_shops_title
import `in`.koreatech.business.core.designsystem.noRippleClickable
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.domain.model.store.OwnerShop
import org.jetbrains.compose.resources.stringResource
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ManageShopsScreen(
    onBack: () -> Unit,
    onRegisterShopClick: () -> Unit,
    onEditShopClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ManageShopsViewModel = metroViewModel()
) {
    val state by viewModel.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val errorMessage = stringResource(Res.string.error_shop_load)
    viewModel.collectSideEffect { snackbarHostState.showSnackbar(errorMessage) }
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.onResume()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            KoinTopAppBar(
                title = { Text(stringResource(Res.string.manage_shops_title), style = KoinTheme.typography.medium18) },
                onNavigationIconClick = onBack
            )
        },
        bottomBar = {
            Button(
                onClick = onRegisterShopClick,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = KoinTheme.colors.primary500)
            ) {
                Text(
                    text = stringResource(Res.string.manage_shops_register),
                    style = KoinTheme.typography.medium16,
                    color = Color.White
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color(0xFFF8F8FA)
    ) { paddingValues ->
        ManageShopsScreenImpl(
            state = state,
            onShopClick = viewModel::selectShop,
            onEditShopClick = onEditShopClick,
            onRetry = viewModel::retry,
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        )
    }
}

@Composable
fun ManageShopsScreenImpl(
    state: ManageShopsState,
    onShopClick: (Int) -> Unit,
    onEditShopClick: (Int) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        state.isLoading -> KoinLoadingContent(modifier)
        state.shops.isEmpty() ->
            KoinEmptyContent(
                message = stringResource(Res.string.manage_shops_empty),
                modifier = modifier
            )
        else ->
            LazyColumn(
                modifier = modifier,
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.shops, key = OwnerShop::id) { shop ->
                    ManageShopItem(
                        shop = shop,
                        selected = state.selectedShopId == shop.id,
                        onClick = { onShopClick(shop.id) },
                        onEdit = { onEditShopClick(shop.id) }
                    )
                }
            }
    }
}

@Composable
private fun ManageShopItem(
    shop: OwnerShop,
    selected: Boolean,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier =
        modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(
                width = if (selected) 1.5.dp else 0.5.dp,
                color = if (selected) KoinTheme.colors.primary500 else KoinTheme.colors.neutral300,
                shape = RoundedCornerShape(12.dp)
            ).noRippleClickable(onClick = onClick)
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = shop.name,
                style = KoinTheme.typography.medium16,
                color = KoinTheme.colors.neutral800,
                modifier = Modifier.weight(1f)
            )
            if (selected) {
                Text(
                    text = stringResource(Res.string.manage_shops_current),
                    style = KoinTheme.typography.medium13,
                    color = KoinTheme.colors.primary500
                )
            }
            Text(
                text = stringResource(Res.string.common_edit),
                style = KoinTheme.typography.medium13,
                color = KoinTheme.colors.primary500,
                modifier = Modifier.padding(start = 12.dp).noRippleClickable(onClick = onEdit)
            )
        }
        Text(
            text =
            shop.address.orEmpty().ifBlank {
                stringResource(Res.string.manage_shops_address_not_registered)
            },
            style = KoinTheme.typography.regular13,
            color = KoinTheme.colors.neutral500
        )
    }
}
