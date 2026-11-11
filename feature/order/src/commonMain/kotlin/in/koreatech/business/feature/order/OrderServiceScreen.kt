package `in`.koreatech.business.feature.order

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.zacsweers.metrox.viewmodel.metroViewModel
import `in`.koreatech.business.core.designsystem.component.KoinEmptyContent
import `in`.koreatech.business.core.designsystem.component.KoinErrorContent
import `in`.koreatech.business.core.designsystem.component.topbar.KoinTopAppBar
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.order_load_error
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_form_title
import `in`.koreatech.business.core.designsystem.generated.resources.order_shop_unavailable
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import org.jetbrains.compose.resources.stringResource
import org.orbitmvi.orbit.compose.collectAsState

@Composable
internal fun OrderServiceScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = metroViewModel()
) {
    val state by viewModel.collectAsState()
    LaunchedEffect(state.shopId) {
        if (state.shopId != null) viewModel.startOrderService()
    }
    Scaffold(
        modifier = modifier.fillMaxSize().imePadding(),
        containerColor = KoinTheme.colors.neutral75,
        topBar = {
            KoinTopAppBar(
                title = { Text(stringResource(Res.string.order_service_form_title), style = KoinTheme.typography.medium18) },
                onNavigationIconClick = onBack
            )
        }
    ) { paddingValues ->
        val contentModifier = Modifier.padding(paddingValues)
        when {
            state.isLoading -> Box(contentModifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = KoinTheme.colors.primary500)
            }
            state.hasError -> KoinErrorContent(stringResource(Res.string.order_load_error), viewModel::retry, contentModifier)
            state.shopId == null || state.orderableShopId != null -> KoinEmptyContent(
                stringResource(Res.string.order_shop_unavailable),
                modifier = contentModifier
            )
            else -> OrderServiceContent(
                state = state.service,
                onFieldChange = viewModel::updateServiceField,
                onDeliveryOptionChange = viewModel::updateDeliveryOption,
                onTakeoutChange = viewModel::updateTakeout,
                onMenuToggle = viewModel::toggleServiceMenu,
                onUploadDocument = viewModel::uploadServiceDocument,
                onFileSelectionFailed = viewModel::fileSelectionFailed,
                onRetryMenus = viewModel::retryServiceMenus,
                onSubmit = viewModel::submitOrderService,
                modifier = contentModifier
            )
        }
    }
}
