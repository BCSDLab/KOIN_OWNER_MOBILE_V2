package `in`.koreatech.business.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_forward_symbol
import `in`.koreatech.business.core.designsystem.generated.resources.common_none
import `in`.koreatech.business.core.designsystem.generated.resources.common_won_format
import `in`.koreatech.business.core.designsystem.generated.resources.home_address
import `in`.koreatech.business.core.designsystem.generated.resources.home_available
import `in`.koreatech.business.core.designsystem.generated.resources.home_bank_transfer
import `in`.koreatech.business.core.designsystem.generated.resources.home_card
import `in`.koreatech.business.core.designsystem.generated.resources.home_category_not_registered
import `in`.koreatech.business.core.designsystem.generated.resources.home_delivery
import `in`.koreatech.business.core.designsystem.generated.resources.home_delivery_fee
import `in`.koreatech.business.core.designsystem.generated.resources.home_event_in_progress
import `in`.koreatech.business.core.designsystem.generated.resources.home_image_count
import `in`.koreatech.business.core.designsystem.generated.resources.home_information
import `in`.koreatech.business.core.designsystem.generated.resources.home_operating_time
import `in`.koreatech.business.core.designsystem.generated.resources.home_phone
import `in`.koreatech.business.core.designsystem.generated.resources.home_settlement_account
import `in`.koreatech.business.core.designsystem.generated.resources.home_shop_image
import `in`.koreatech.business.core.designsystem.generated.resources.operating_time_closed
import `in`.koreatech.business.core.designsystem.generated.resources.weekday_friday
import `in`.koreatech.business.core.designsystem.generated.resources.weekday_monday
import `in`.koreatech.business.core.designsystem.generated.resources.weekday_saturday
import `in`.koreatech.business.core.designsystem.generated.resources.weekday_sunday
import `in`.koreatech.business.core.designsystem.generated.resources.weekday_thursday
import `in`.koreatech.business.core.designsystem.generated.resources.weekday_tuesday
import `in`.koreatech.business.core.designsystem.generated.resources.weekday_wednesday
import `in`.koreatech.business.core.designsystem.noRippleClickable
import `in`.koreatech.business.core.designsystem.component.skeleton
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.domain.model.store.OwnerShop
import `in`.koreatech.business.feature.home.util.toDisplayText
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ManagedShopCard(
    shop: OwnerShop,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val delivery = stringResource(Res.string.home_delivery)
    val card = stringResource(Res.string.home_card)
    val bankTransfer = stringResource(Res.string.home_bank_transfer)
    val categoryNotRegistered = stringResource(Res.string.home_category_not_registered)
    val none = stringResource(Res.string.common_none)
    val closed = stringResource(Res.string.operating_time_closed)
    val dayNames = mapOf(
        "MONDAY" to stringResource(Res.string.weekday_monday),
        "TUESDAY" to stringResource(Res.string.weekday_tuesday),
        "WEDNESDAY" to stringResource(Res.string.weekday_wednesday),
        "THURSDAY" to stringResource(Res.string.weekday_thursday),
        "FRIDAY" to stringResource(Res.string.weekday_friday),
        "SATURDAY" to stringResource(Res.string.weekday_saturday),
        "SUNDAY" to stringResource(Res.string.weekday_sunday)
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .managedShopCard()
            .noRippleClickable(onClick = onClick)
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ShopBadge(shop.name.take(1))
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = shop.name,
                        style = KoinTheme.typography.medium16,
                        color = KoinTheme.colors.neutral800,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    if (shop.hasActiveEvent) {
                        Spacer(modifier = Modifier.width(8.dp))
                        EventChip()
                    }
                }
                Text(
                    text = shop.categories.joinToString(" · ").ifBlank { categoryNotRegistered },
                    style = KoinTheme.typography.regular13,
                    color = KoinTheme.colors.neutral450
                )
            }
            HomeArrow()
        }
        Spacer(modifier = Modifier.height(16.dp))
        shop.address?.takeIf(String::isNotBlank)?.let {
            HomeDetailRow(stringResource(Res.string.home_address), it)
        }
        shop.phone?.takeIf(String::isNotBlank)?.let {
            HomeDetailRow(stringResource(Res.string.home_phone), it)
        }
        shop.description?.takeIf(String::isNotBlank)?.let {
            HomeDetailRow(stringResource(Res.string.home_information), it)
        }
        if (shop.operatingTimes.isNotEmpty()) {
            HomeDetailRow(
                label = stringResource(Res.string.home_operating_time),
                value = shop.operatingTimes.joinToString("\n") { it.toDisplayText(dayNames, closed) }
            )
        }
        HomeDetailRow(
            label = stringResource(Res.string.home_available),
            value = listOfNotNull(
                delivery.takeIf { shop.isDeliveryAvailable },
                card.takeIf { shop.isCardAvailable },
                bankTransfer.takeIf { shop.isBankTransferAvailable }
            ).joinToString(" · ").ifBlank { none }
        )
        if (shop.isDeliveryAvailable) {
            HomeDetailRow(
                label = stringResource(Res.string.home_delivery_fee),
                value = stringResource(Res.string.common_won_format, shop.deliveryPrice)
            )
        }
        if (!shop.bank.isNullOrBlank() || !shop.accountNumber.isNullOrBlank()) {
            HomeDetailRow(
                label = stringResource(Res.string.home_settlement_account),
                value = listOfNotNull(shop.bank, shop.accountNumber).joinToString(" ")
            )
        }
        if (shop.imageUrls.isNotEmpty()) {
            HomeDetailRow(
                label = stringResource(Res.string.home_shop_image),
                value = stringResource(Res.string.home_image_count, shop.imageUrls.size)
            )
        }
    }
}

@Composable
internal fun ManagedShopCardSkeleton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .managedShopCard()
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(40.dp).skeleton(shape = RoundedCornerShape(12.dp)))
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f), verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(6.dp)) {
                Box(modifier = Modifier.fillMaxWidth(0.5f).height(20.dp).skeleton())
                Box(modifier = Modifier.fillMaxWidth(0.35f).height(14.dp).skeleton())
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        repeat(4) {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 6.dp)) {
                Box(modifier = Modifier.width(72.dp).height(14.dp).skeleton())
                Box(modifier = Modifier.fillMaxWidth().height(14.dp).skeleton())
            }
        }
    }
}

@Composable
private fun HomeDetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth().padding(top = 6.dp)) {
        Text(
            text = label,
            modifier = Modifier.width(72.dp),
            style = KoinTheme.typography.medium13,
            color = KoinTheme.colors.neutral500
        )
        Text(
            text = value,
            modifier = Modifier.weight(1f),
            style = KoinTheme.typography.regular13,
            color = KoinTheme.colors.neutral700
        )
    }
}

@Composable
private fun EventChip(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(KoinTheme.colors.primary100)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(Res.string.home_event_in_progress),
            style = KoinTheme.typography.medium12,
            color = KoinTheme.colors.primary600
        )
    }
}

@Composable
private fun ShopBadge(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(KoinTheme.colors.primary100),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = KoinTheme.typography.bold16,
            color = KoinTheme.colors.primary600
        )
    }
}

@Composable
private fun HomeArrow(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(Res.string.common_forward_symbol),
        style = KoinTheme.typography.medium18,
        color = KoinTheme.colors.neutral500,
        modifier = modifier
    )
}

@Composable
private fun Modifier.managedShopCard(): Modifier = border(
    width = 0.5.dp,
    color = KoinTheme.colors.neutral250,
    shape = RoundedCornerShape(16.dp)
).clip(RoundedCornerShape(16.dp)).background(Color.White)
