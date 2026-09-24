package `in`.koreatech.business.feature.order

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.zacsweers.metrox.viewmodel.metroViewModel
import `in`.koreatech.business.core.designsystem.component.KoinEmptyContent
import `in`.koreatech.business.core.designsystem.component.KoinErrorContent
import `in`.koreatech.business.core.designsystem.component.KoinScreenTitle
import `in`.koreatech.business.core.designsystem.component.KoinSelectableChipGroup
import `in`.koreatech.business.core.designsystem.component.KoinSelectableItem
import `in`.koreatech.business.core.designsystem.component.skeleton
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_won_format
import `in`.koreatech.business.core.designsystem.generated.resources.order_empty
import `in`.koreatech.business.core.designsystem.generated.resources.order_load_error
import `in`.koreatech.business.core.designsystem.generated.resources.order_number
import `in`.koreatech.business.core.designsystem.generated.resources.order_shop_unavailable
import `in`.koreatech.business.core.designsystem.generated.resources.order_status_completed
import `in`.koreatech.business.core.designsystem.generated.resources.order_status_cooking
import `in`.koreatech.business.core.designsystem.generated.resources.order_status_delivering
import `in`.koreatech.business.core.designsystem.generated.resources.order_status_new
import `in`.koreatech.business.core.designsystem.generated.resources.order_title
import `in`.koreatech.business.core.designsystem.generated.resources.order_type_delivery
import `in`.koreatech.business.core.designsystem.generated.resources.order_type_takeout
import `in`.koreatech.business.core.designsystem.noRippleClickable
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.core.util.toCurrencyText
import `in`.koreatech.business.domain.model.order.OwnerOrder
import `in`.koreatech.business.domain.model.order.OwnerOrderCategory
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun OrderScreen(
    onOrderClick: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = metroViewModel()
) {
    val state by viewModel.collectAsState()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = KoinTheme.colors.neutral75,
        topBar = {
            KoinScreenTitle(
                text = stringResource(Res.string.order_title),
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            OrderCategoryTabs(state.category, viewModel::selectCategory)
            when {
                state.isLoading -> OrderLoadingContent()
                state.hasError -> KoinErrorContent(stringResource(Res.string.order_load_error), viewModel::retry)
                state.orderableShopId == null -> KoinEmptyContent(stringResource(Res.string.order_shop_unavailable))
                state.orders.isEmpty() -> KoinEmptyContent(stringResource(Res.string.order_empty))
                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.orders, key = { it.id }) { order ->
                        OrderCard(order) { onOrderClick(requireNotNull(state.orderableShopId), order.id) }
                    }
                }
            }
        }
    }
}

@Composable
private fun OrderLoadingContent() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(4) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(KoinTheme.colors.neutral100, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.width(136.dp).height(20.dp).skeleton())
                    Spacer(modifier = Modifier.weight(1f))
                    Box(modifier = Modifier.width(40.dp).height(18.dp).skeleton())
                }
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = KoinTheme.colors.neutral300)
                Spacer(modifier = Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Box(modifier = Modifier.width(72.dp).height(18.dp).skeleton())
                        Box(modifier = Modifier.width(112.dp).height(14.dp).skeleton())
                    }
                    Box(modifier = Modifier.width(72.dp).height(20.dp).skeleton())
                }
            }
        }
    }
}

@Composable
private fun OrderCategoryTabs(selected: OwnerOrderCategory, onSelect: (OwnerOrderCategory) -> Unit) {
    val categories = OwnerOrderCategory.entries
    KoinSelectableChipGroup(
        items = categories.mapIndexed { index, category ->
            KoinSelectableItem(index, stringResource(category.label))
        },
        selectedItemId = categories.indexOf(selected),
        onItemClick = { index -> categories.getOrNull(index)?.let(onSelect) },
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
    )
}

@Composable
private fun OrderCard(order: OwnerOrder, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(KoinTheme.colors.neutral100)
            .border(0.5.dp, KoinTheme.colors.neutral300, RoundedCornerShape(12.dp))
            .noRippleClickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(Res.string.order_number, order.number),
                modifier = Modifier.weight(1f),
                style = KoinTheme.typography.medium16,
                color = KoinTheme.colors.neutral800
            )
            Text(
                text = stringResource(order.typeLabel),
                style = KoinTheme.typography.medium13,
                color = KoinTheme.colors.primary500
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(color = KoinTheme.colors.neutral300)
        Spacer(modifier = Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(order.statusLabel),
                    style = KoinTheme.typography.medium14,
                    color = KoinTheme.colors.neutral700
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = order.orderedAt.readableDateTime(),
                    style = KoinTheme.typography.regular12,
                    color = KoinTheme.colors.neutral500
                )
            }
            Text(
                text = stringResource(Res.string.common_won_format, order.totalPrice.toCurrencyText()),
                style = KoinTheme.typography.bold16,
                color = KoinTheme.colors.neutral800
            )
        }
    }
}

internal val OwnerOrderCategory.label: StringResource
    get() = when (this) {
        OwnerOrderCategory.NEW -> Res.string.order_status_new
        OwnerOrderCategory.COOKING -> Res.string.order_status_cooking
        OwnerOrderCategory.DELIVERING -> Res.string.order_status_delivering
        OwnerOrderCategory.COMPLETED -> Res.string.order_status_completed
    }

internal val OwnerOrder.typeLabel: StringResource
    get() = if (type == "DELIVERY") Res.string.order_type_delivery else Res.string.order_type_takeout

internal val OwnerOrder.statusLabel: StringResource
    get() = when (status) {
        "CONFIRMING" -> Res.string.order_status_new
        "COOKING" -> Res.string.order_status_cooking
        "DELIVERING", "PACKAGED" -> Res.string.order_status_delivering
        else -> Res.string.order_status_completed
    }

internal fun String.readableDateTime(): String = replace('T', ' ').take(16)
