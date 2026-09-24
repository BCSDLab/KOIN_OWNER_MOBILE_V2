package `in`.koreatech.business.feature.order

import androidx.compose.foundation.background
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.zacsweers.metrox.viewmodel.metroViewModel
import `in`.koreatech.business.core.designsystem.component.KoinErrorContent
import `in`.koreatech.business.core.designsystem.component.skeleton
import `in`.koreatech.business.core.designsystem.component.topbar.KoinTopAppBar
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_none
import `in`.koreatech.business.core.designsystem.generated.resources.common_won_format
import `in`.koreatech.business.core.designsystem.generated.resources.order_delivery_tip
import `in`.koreatech.business.core.designsystem.generated.resources.order_detail_title
import `in`.koreatech.business.core.designsystem.generated.resources.order_discount
import `in`.koreatech.business.core.designsystem.generated.resources.order_load_error
import `in`.koreatech.business.core.designsystem.generated.resources.order_menu_quantity
import `in`.koreatech.business.core.designsystem.generated.resources.order_menu_title
import `in`.koreatech.business.core.designsystem.generated.resources.order_number
import `in`.koreatech.business.core.designsystem.generated.resources.order_option_format
import `in`.koreatech.business.core.designsystem.generated.resources.order_ordered_at
import `in`.koreatech.business.core.designsystem.generated.resources.order_payment_title
import `in`.koreatech.business.core.designsystem.generated.resources.order_product_price
import `in`.koreatech.business.core.designsystem.generated.resources.order_receiver_address
import `in`.koreatech.business.core.designsystem.generated.resources.order_receiver_name
import `in`.koreatech.business.core.designsystem.generated.resources.order_receiver_phone
import `in`.koreatech.business.core.designsystem.generated.resources.order_receiver_title
import `in`.koreatech.business.core.designsystem.generated.resources.order_request_owner
import `in`.koreatech.business.core.designsystem.generated.resources.order_request_rider
import `in`.koreatech.business.core.designsystem.generated.resources.order_total_price
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.core.util.toCurrencyText
import `in`.koreatech.business.core.util.toKRPhoneNumber
import `in`.koreatech.business.domain.model.order.OwnerOrderDetail
import org.jetbrains.compose.resources.stringResource
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun OrderDetailScreen(
    orderableShopId: Int,
    orderId: Int,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: OrderDetailViewModel = metroViewModel()
) {
    val state by viewModel.collectAsState()
    LaunchedEffect(orderableShopId, orderId) { viewModel.load(orderableShopId, orderId) }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = KoinTheme.colors.neutral75,
        topBar = {
            KoinTopAppBar(
                title = { Text(stringResource(Res.string.order_detail_title), style = KoinTheme.typography.medium18) },
                onNavigationIconClick = onBack
            )
        }
    ) { paddingValues ->
        when {
            state.isLoading -> OrderDetailLoadingContent(Modifier.padding(paddingValues))
            state.hasError -> KoinErrorContent(
                stringResource(Res.string.order_load_error),
                { viewModel.load(orderableShopId, orderId) },
                Modifier.padding(paddingValues)
            )

            state.order != null -> OrderDetailContent(requireNotNull(state.order), Modifier.padding(paddingValues))
        }
    }
}

@Composable
private fun OrderDetailLoadingContent(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { DetailSectionSkeleton(titleWidth = 144.dp, rowWidths = listOf(180.dp, 120.dp)) }
        item { Box(modifier = Modifier.width(88.dp).height(22.dp).skeleton()) }
        items(2) {
            DetailSectionSkeleton(
                titleWidth = 156.dp,
                rowWidths = listOf(112.dp, 196.dp, 72.dp)
            )
        }
        item { Box(modifier = Modifier.width(104.dp).height(22.dp).skeleton()) }
        item {
            DetailSectionSkeleton(
                rowWidths = listOf(128.dp, 152.dp, 220.dp, 184.dp)
            )
        }
        item { Box(modifier = Modifier.width(88.dp).height(22.dp).skeleton()) }
        item {
            DetailSectionSkeleton(
                rowWidths = listOf(112.dp, 96.dp, 104.dp, 136.dp)
            )
        }
    }
}

@Composable
private fun DetailSectionSkeleton(
    titleWidth: Dp? = null,
    rowWidths: List<Dp>
) {
    Column(
        modifier = Modifier.fillMaxWidth().background(KoinTheme.colors.neutral100, KoinTheme.shapes.small).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        titleWidth?.let {
            Box(modifier = Modifier.width(it).height(20.dp).skeleton())
            HorizontalDivider(color = KoinTheme.colors.neutral300)
        }
        rowWidths.forEachIndexed { index, width ->
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.width(if (index % 2 == 0) 72.dp else 92.dp).height(16.dp).skeleton())
                Spacer(modifier = Modifier.weight(1f))
                Box(modifier = Modifier.width(width).height(16.dp).skeleton())
            }
        }
    }
}

@Composable
private fun OrderDetailContent(
    order: OwnerOrderDetail,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            DetailSection(stringResource(Res.string.order_number, order.number)) {
                DetailRow(stringResource(Res.string.order_ordered_at), order.orderedAt.readableDateTime())
                DetailRow(stringResource(order.toSummary().typeLabel), stringResource(order.toSummary().statusLabel))
            }
        }
        item { SectionTitle(stringResource(Res.string.order_menu_title)) }
        items(order.menus, key = { it.id }) { menu ->
            DetailSection(stringResource(Res.string.order_menu_quantity, menu.name, menu.quantity)) {
                menu.priceName?.let { Text(it, style = KoinTheme.typography.regular13, color = KoinTheme.colors.neutral600) }
                menu.options.forEach { option ->
                    Text(
                        text = stringResource(
                            Res.string.order_option_format,
                            option.groupName,
                            option.name,
                            option.quantity
                        ),
                        style = KoinTheme.typography.regular13,
                        color = KoinTheme.colors.neutral600
                    )
                }
                DetailRow(
                    "",
                    stringResource(Res.string.common_won_format, (menu.price * menu.quantity).toCurrencyText())
                )
            }
        }
        item {
            SectionTitle(stringResource(Res.string.order_receiver_title))
            DetailSection {
                DetailRow(stringResource(Res.string.order_receiver_name), order.receiver.name)
                DetailRow(stringResource(Res.string.order_receiver_phone), order.receiver.phoneNumber.toKRPhoneNumber())
                DetailRow(
                    stringResource(Res.string.order_receiver_address),
                    listOfNotNull(order.receiver.address, order.receiver.addressDetail).joinToString(" ").ifBlank {
                        stringResource(Res.string.common_none)
                    }
                )
                DetailRow(stringResource(Res.string.order_request_owner), order.receiver.toOwner.orNone())
                order.receiver.toRider?.let { DetailRow(stringResource(Res.string.order_request_rider), it) }
            }
        }
        item {
            SectionTitle(stringResource(Res.string.order_payment_title))
            DetailSection {
                DetailRow(
                    stringResource(Res.string.order_product_price),
                    stringResource(Res.string.common_won_format, order.payment.totalProductPrice.toCurrencyText())
                )
                DetailRow(
                    stringResource(Res.string.order_delivery_tip),
                    stringResource(Res.string.common_won_format, order.payment.deliveryTip.toCurrencyText())
                )
                DetailRow(
                    stringResource(Res.string.order_discount),
                    stringResource(Res.string.common_won_format, order.payment.discountAmount.toCurrencyText())
                )
                HorizontalDivider(color = KoinTheme.colors.neutral300)
                DetailRow(
                    stringResource(Res.string.order_total_price),
                    stringResource(Res.string.common_won_format, order.payment.totalPrice.toCurrencyText()),
                    emphasized = true
                )
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text = text, style = KoinTheme.typography.bold18, color = KoinTheme.colors.neutral800)
}

@Composable
private fun DetailSection(title: String? = null, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().background(KoinTheme.colors.neutral100, KoinTheme.shapes.small).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        title?.let {
            Text(text = it, style = KoinTheme.typography.medium16, color = KoinTheme.colors.neutral800)
            HorizontalDivider(color = KoinTheme.colors.neutral300)
        }
        content()
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    emphasized: Boolean = false
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = KoinTheme.typography.regular14,
            color = KoinTheme.colors.neutral600
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = value,
            modifier = Modifier.weight(1f).fillMaxWidth(),
            textAlign = TextAlign.End,
            style = if (emphasized) KoinTheme.typography.bold16 else KoinTheme.typography.medium14,
            color = KoinTheme.colors.neutral800
        )
    }
}

@Composable
private fun String?.orNone() = if (isNullOrBlank()) stringResource(Res.string.common_none) else this

private fun OwnerOrderDetail.toSummary() = `in`.koreatech.business.domain.model.order.OwnerOrder(
    id,
    number,
    type,
    status,
    orderedAt,
    null,
    payment.totalPrice
)
