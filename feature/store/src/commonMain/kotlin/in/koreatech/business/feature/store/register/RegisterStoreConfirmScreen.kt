package `in`.koreatech.business.feature.store.register

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.component.topbar.KoinTopAppBar
import `in`.koreatech.business.core.designsystem.component.user.KoinUserProgressHeader
import `in`.koreatech.business.core.designsystem.component.user.KoinUserProgressIndicator
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_check_symbol
import `in`.koreatech.business.core.designsystem.generated.resources.common_none
import `in`.koreatech.business.core.designsystem.generated.resources.home_available
import `in`.koreatech.business.core.designsystem.generated.resources.home_bank_transfer
import `in`.koreatech.business.core.designsystem.generated.resources.home_card
import `in`.koreatech.business.core.designsystem.generated.resources.home_delivery
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_address
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_closed
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_complete
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_complete_description
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_confirm_description
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_confirm_question
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_delivery_fee
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_edit
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_edit_complete
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_go_home
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_intro_category
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_intro_confirm
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_name
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_open_24_hours
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_operating_time
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_other_information
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_phone_number
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_register
import `in`.koreatech.business.core.designsystem.generated.resources.weekday_friday
import `in`.koreatech.business.core.designsystem.generated.resources.weekday_monday
import `in`.koreatech.business.core.designsystem.generated.resources.weekday_saturday
import `in`.koreatech.business.core.designsystem.generated.resources.weekday_sunday
import `in`.koreatech.business.core.designsystem.generated.resources.weekday_thursday
import `in`.koreatech.business.core.designsystem.generated.resources.weekday_tuesday
import `in`.koreatech.business.core.designsystem.generated.resources.weekday_wednesday
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RegisterStoreConfirmScreen(
    state: RegisterStoreState,
    title: String,
    onBack: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    val none = stringResource(Res.string.common_none)
    val closed = stringResource(Res.string.register_store_closed)
    val open24Hours = stringResource(Res.string.register_store_open_24_hours)
    val weekdayLabels =
        mapOf(
            RegisterStoreDay.Monday to stringResource(Res.string.weekday_monday),
            RegisterStoreDay.Tuesday to stringResource(Res.string.weekday_tuesday),
            RegisterStoreDay.Wednesday to stringResource(Res.string.weekday_wednesday),
            RegisterStoreDay.Thursday to stringResource(Res.string.weekday_thursday),
            RegisterStoreDay.Friday to stringResource(Res.string.weekday_friday),
            RegisterStoreDay.Saturday to stringResource(Res.string.weekday_saturday),
            RegisterStoreDay.Sunday to stringResource(Res.string.weekday_sunday)
        )
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color(0xFFF8F8FA),
        topBar = {
            KoinTopAppBar(
                modifier = Modifier.fillMaxWidth().background(KoinTheme.colors.neutral0),
                title = { Text(title, style = KoinTheme.typography.medium18) },
                onNavigationIconClick = onBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 24.dp)
        ) {
            KoinUserProgressHeader(stringResource(Res.string.register_store_intro_confirm), 4, 4)
            Spacer(Modifier.height(8.dp))
            KoinUserProgressIndicator(4, 4)
            Spacer(Modifier.height(48.dp))
            Text(stringResource(Res.string.register_store_confirm_question), style = KoinTheme.typography.medium18)
            Spacer(Modifier.height(8.dp))
            Text(
                stringResource(Res.string.register_store_confirm_description),
                style = KoinTheme.typography.regular13,
                color = KoinTheme.colors.neutral500
            )
            Spacer(Modifier.height(24.dp))
            Column(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(KoinTheme.colors.neutral0)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ConfirmItem(
                    stringResource(Res.string.register_store_intro_category),
                    state.categories
                        .firstOrNull { it.id == state.selectedCategoryId }
                        ?.name
                        .orEmpty()
                )
                ConfirmItem(stringResource(Res.string.register_store_name), state.storeName)
                ConfirmItem(stringResource(Res.string.register_store_address), state.address)
                ConfirmItem(stringResource(Res.string.register_store_phone_number), state.phoneNumber)
                ConfirmItem(stringResource(Res.string.register_store_delivery_fee), state.deliveryFee.ifBlank { none })
                ConfirmItem(
                    stringResource(Res.string.register_store_operating_time),
                    state.operatingTimes
                        .joinToString("\n") { operatingTime ->
                            val days =
                                operatingTime.days.joinToString(" · ") { day ->
                                    weekdayLabels.getValue(day)
                                }
                            val time =
                                if (operatingTime.is24Hours) {
                                    open24Hours
                                } else {
                                    "${operatingTime.openingTime}–${operatingTime.closingTime}"
                                }
                            "$days $time"
                        }.ifBlank { closed }
                )
                ConfirmItem(stringResource(Res.string.register_store_other_information), state.otherInfo.ifBlank { none })
                ConfirmItem(
                    stringResource(Res.string.home_available),
                    listOfNotNull(
                        stringResource(Res.string.home_delivery).takeIf { state.isDeliveryAvailable },
                        stringResource(Res.string.home_card).takeIf { state.isCardAvailable },
                        stringResource(Res.string.home_bank_transfer).takeIf { state.isBankTransferAvailable }
                    ).joinToString().ifBlank { none }
                )
            }
            Spacer(Modifier.weight(1f))
            Spacer(Modifier.height(40.dp))
            Button(
                onClick = onConfirm,
                enabled = !state.isSaving,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors =
                ButtonDefaults.buttonColors(
                    containerColor = KoinTheme.colors.primary500,
                    contentColor = KoinTheme.colors.neutral0
                ),
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                Text(
                    stringResource(
                        if (state.shopId == null) Res.string.register_store_register else Res.string.register_store_edit
                    ),
                    style = KoinTheme.typography.medium15
                )
            }
        }
    }
}

@Composable
private fun ConfirmItem(
    label: String,
    value: String
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        Text(label, modifier = Modifier.weight(0.35f), style = KoinTheme.typography.medium14, color = KoinTheme.colors.neutral500)
        Text(value, modifier = Modifier.weight(0.65f), style = KoinTheme.typography.regular14, color = KoinTheme.colors.neutral800)
    }
}

@Composable
internal fun RegisterStoreCompleteScreen(
    isEditing: Boolean,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier.fillMaxSize(), containerColor = Color(0xFFF8F8FA)) { paddingValues ->
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier =
                        Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(36.dp))
                            .background(KoinTheme.colors.primary100),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            stringResource(Res.string.common_check_symbol),
                            style = KoinTheme.typography.bold20,
                            color = KoinTheme.colors.primary600
                        )
                    }
                    Spacer(Modifier.height(24.dp))
                    Text(
                        stringResource(
                            if (isEditing) Res.string.register_store_edit_complete else Res.string.register_store_complete
                        ),
                        style = KoinTheme.typography.bold20,
                        color = KoinTheme.colors.neutral800
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        stringResource(Res.string.register_store_complete_description),
                        style = KoinTheme.typography.regular15,
                        color = KoinTheme.colors.neutral500,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Button(
                onClick = onComplete,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors =
                ButtonDefaults.buttonColors(
                    containerColor = KoinTheme.colors.primary500,
                    contentColor = KoinTheme.colors.neutral0
                ),
                contentPadding = PaddingValues(vertical = 14.dp)
            ) { Text(stringResource(Res.string.register_store_go_home), style = KoinTheme.typography.medium15) }
        }
    }
}
