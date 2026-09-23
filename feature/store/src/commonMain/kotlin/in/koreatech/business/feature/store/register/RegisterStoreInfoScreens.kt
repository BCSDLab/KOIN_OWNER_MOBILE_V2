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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.component.KoinImagePreview
import `in`.koreatech.business.core.designsystem.component.KoinUnderlineTextField
import `in`.koreatech.business.core.designsystem.component.button.primaryButtonColors
import `in`.koreatech.business.core.designsystem.component.topbar.KoinTopAppBar
import `in`.koreatech.business.core.designsystem.component.user.KoinUserProgressHeader
import `in`.koreatech.business.core.designsystem.component.user.KoinUserProgressIndicator
import `in`.koreatech.business.core.designsystem.component.user.SignupCheckBox
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_add_symbol
import `in`.koreatech.business.core.designsystem.generated.resources.common_cancel
import `in`.koreatech.business.core.designsystem.generated.resources.common_closing_time_hint
import `in`.koreatech.business.core.designsystem.generated.resources.common_edit
import `in`.koreatech.business.core.designsystem.generated.resources.common_next
import `in`.koreatech.business.core.designsystem.generated.resources.common_opening_time_hint
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_address
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_address_hint
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_apply
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_available_items
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_bank_transfer_available
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_basic_step
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_card_available
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_closed_all_day
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_closed_day_guide
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_day
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_delivery_available
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_delivery_fee
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_delivery_fee_hint
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_end_time
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_hours_title
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_image_add
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_image_input
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_name
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_name_hint
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_open_24_hours
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_operating_time
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_operation_step
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_other_information
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_other_information_hint
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_phone
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_phone_hint
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_start_time
import `in`.koreatech.business.core.designsystem.generated.resources.weekday_friday
import `in`.koreatech.business.core.designsystem.generated.resources.weekday_monday
import `in`.koreatech.business.core.designsystem.generated.resources.weekday_saturday
import `in`.koreatech.business.core.designsystem.generated.resources.weekday_sunday
import `in`.koreatech.business.core.designsystem.generated.resources.weekday_thursday
import `in`.koreatech.business.core.designsystem.generated.resources.weekday_tuesday
import `in`.koreatech.business.core.designsystem.generated.resources.weekday_wednesday
import `in`.koreatech.business.core.designsystem.noRippleClickable
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.core.file.rememberImageFilePicker
import `in`.koreatech.business.feature.store.register.util.isValidTimeInput
import `in`.koreatech.business.feature.store.register.util.toTimeText
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RegisterStoreBasicInfoScreen(
    title: String,
    state: RegisterStoreState,
    onStoreNameChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onUploadImage: (String, String, ByteArray) -> Unit,
    onImageSelectionFailed: () -> Unit,
    onRemoveImage: (Int) -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    val openImagePicker = rememberImageFilePicker(
        onImagePicked = { onUploadImage(it.name, it.contentType, it.bytes) },
        onFailure = { onImageSelectionFailed() }
    )
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = KoinTheme.colors.neutral75,
        topBar = {
            KoinTopAppBar(
                modifier = Modifier.fillMaxWidth().background(KoinTheme.colors.neutral0),
                title = { Text(title, style = KoinTheme.typography.medium18) },
                onNavigationIconClick = onBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 24.dp)
        ) {
            KoinUserProgressHeader(stringResource(Res.string.register_store_basic_step), 2, 4)
            Spacer(Modifier.height(8.dp))
            KoinUserProgressIndicator(2, 4)
            Spacer(Modifier.height(48.dp))
            Text(stringResource(Res.string.register_store_image_input), style = KoinTheme.typography.medium16)
            Spacer(Modifier.height(12.dp))
            if (state.imageUrls.isNotEmpty()) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    state.imageUrls.forEachIndexed { index, imageUrl ->
                        KoinImagePreview(
                            imageUrl = imageUrl,
                            contentDescription = stringResource(Res.string.register_store_image_input),
                            onDelete = { onRemoveImage(index) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    repeat(5 - state.imageUrls.size) { Spacer(modifier = Modifier.weight(1f)) }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            Button(
                onClick = openImagePicker,
                enabled = !state.isUploading && state.imageUrls.size < 5,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = KoinTheme.colors.primary100,
                    contentColor = KoinTheme.colors.primary600
                )
            ) {
                Text(stringResource(Res.string.register_store_image_add))
            }
            Spacer(Modifier.height(32.dp))
            RegisterStoreField(
                stringResource(Res.string.register_store_name),
                state.storeName,
                stringResource(Res.string.register_store_name_hint)
            ) {
                onStoreNameChange(it)
            }
            Spacer(Modifier.height(24.dp))
            RegisterStoreField(
                stringResource(Res.string.register_store_address),
                state.address,
                stringResource(Res.string.register_store_address_hint)
            ) {
                onAddressChange(it)
            }
            Spacer(Modifier.weight(1f))
            Spacer(Modifier.height(32.dp))
            Button(
                onClick = onNext,
                enabled = state.isBasicInfoValid && !state.isUploading,
                modifier = Modifier.fillMaxWidth(),
                shape = KoinTheme.shapes.small,
                colors = primaryButtonColors(),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) { Text(stringResource(Res.string.common_next), style = KoinTheme.typography.medium16) }
        }
    }
}

@Composable
internal fun RegisterStoreDetailInfoScreen(
    title: String,
    state: RegisterStoreState,
    onPhoneNumberChange: (String) -> Unit,
    onDeliveryFeeChange: (String) -> Unit,
    onOtherInfoChange: (String) -> Unit,
    onOperatingTimesChange: (ImmutableList<RegisterStoreOperatingTime>) -> Unit,
    onDeliveryAvailabilityChange: (Boolean) -> Unit,
    onCardAvailabilityChange: (Boolean) -> Unit,
    onBankTransferAvailabilityChange: (Boolean) -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    val weekdayLabels = mapOf(
        RegisterStoreDay.Monday to stringResource(Res.string.weekday_monday),
        RegisterStoreDay.Tuesday to stringResource(Res.string.weekday_tuesday),
        RegisterStoreDay.Wednesday to stringResource(Res.string.weekday_wednesday),
        RegisterStoreDay.Thursday to stringResource(Res.string.weekday_thursday),
        RegisterStoreDay.Friday to stringResource(Res.string.weekday_friday),
        RegisterStoreDay.Saturday to stringResource(Res.string.weekday_saturday),
        RegisterStoreDay.Sunday to stringResource(Res.string.weekday_sunday)
    )
    val closedAllDay = stringResource(Res.string.register_store_closed_all_day)
    var showOperatingTimeDialog by remember { mutableStateOf(false) }
    var editingOperatingTimeIndex by remember { mutableStateOf<Int?>(null) }
    var openingTimeInput by remember { mutableStateOf(DEFAULT_OPENING_TIME.filter(Char::isDigit)) }
    var closingTimeInput by remember { mutableStateOf(DEFAULT_CLOSING_TIME.filter(Char::isDigit)) }
    var selectedDays by remember { mutableStateOf(emptySet<RegisterStoreDay>().toImmutableSet()) }
    var is24Hours by remember { mutableStateOf(false) }

    if (showOperatingTimeDialog) {
        AlertDialog(
            onDismissRequest = { showOperatingTimeDialog = false },
            title = { Text(stringResource(Res.string.register_store_hours_title), style = KoinTheme.typography.medium18) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(stringResource(Res.string.register_store_day), style = KoinTheme.typography.medium14)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        RegisterStoreDay.entries.forEach { day ->
                            val selected = day in selectedDays
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (selected) KoinTheme.colors.primary500 else KoinTheme.colors.neutral100)
                                    .noRippleClickable {
                                        selectedDays = if (selected) {
                                            (selectedDays - day).toImmutableSet()
                                        } else {
                                            (selectedDays + day).toImmutableSet()
                                        }
                                        if (selectedDays.isEmpty()) is24Hours = false
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    weekdayLabels.getValue(day),
                                    style = KoinTheme.typography.medium13,
                                    color = if (selected) KoinTheme.colors.neutral0 else KoinTheme.colors.neutral600
                                )
                            }
                        }
                    }
                    if (selectedDays.isNotEmpty() && !is24Hours) {
                        Text(stringResource(Res.string.register_store_start_time), style = KoinTheme.typography.medium14)
                        KoinUnderlineTextField(
                            value = openingTimeInput,
                            onValueChange = { openingTimeInput = it.filter(Char::isDigit).take(4) },
                            hint = stringResource(Res.string.common_opening_time_hint),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Text(stringResource(Res.string.register_store_end_time), style = KoinTheme.typography.medium14)
                        KoinUnderlineTextField(
                            value = closingTimeInput,
                            onValueChange = { closingTimeInput = it.filter(Char::isDigit).take(4) },
                            hint = stringResource(Res.string.common_closing_time_hint),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                    SignupCheckBox(
                        checked = is24Hours,
                        text = stringResource(Res.string.register_store_open_24_hours),
                        onCheckedChange = {
                            if (selectedDays.isNotEmpty()) is24Hours = it
                        }
                    )
                    Text(
                        stringResource(Res.string.register_store_closed_day_guide),
                        style = KoinTheme.typography.regular12,
                        color = KoinTheme.colors.neutral500
                    )
                }
            },
            confirmButton = {
                TextButton(
                    enabled = selectedDays.isEmpty() || is24Hours ||
                        (openingTimeInput.isValidTimeInput() && closingTimeInput.isValidTimeInput()),
                    onClick = {
                        val updatedOperatingTimes = state.operatingTimes
                            .filterIndexed { index, _ -> index != editingOperatingTimeIndex }
                            .mapNotNull { operatingTime ->
                                val remainingDays = (operatingTime.days - selectedDays).toImmutableSet()
                                operatingTime.copy(days = remainingDays).takeIf { remainingDays.isNotEmpty() }
                            }.toMutableList()
                        if (selectedDays.isNotEmpty()) {
                            updatedOperatingTimes += RegisterStoreOperatingTime(
                                days = selectedDays,
                                openingTime = openingTimeInput.toTimeText(),
                                closingTime = closingTimeInput.toTimeText(),
                                is24Hours = is24Hours
                            )
                        }
                        onOperatingTimesChange(updatedOperatingTimes.toImmutableList())
                        showOperatingTimeDialog = false
                    }
                ) {
                    Text(stringResource(Res.string.register_store_apply), style = KoinTheme.typography.medium14)
                }
            },
            dismissButton = {
                TextButton(onClick = { showOperatingTimeDialog = false }) {
                    Text(stringResource(Res.string.common_cancel), style = KoinTheme.typography.medium14)
                }
            },
            containerColor = KoinTheme.colors.neutral0
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = KoinTheme.colors.neutral75,
        topBar = {
            KoinTopAppBar(
                modifier = Modifier.fillMaxWidth().background(KoinTheme.colors.neutral0),
                title = { Text(title, style = KoinTheme.typography.medium18) },
                onNavigationIconClick = onBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 24.dp)
        ) {
            KoinUserProgressHeader(stringResource(Res.string.register_store_operation_step), 3, 4)
            Spacer(Modifier.height(8.dp))
            KoinUserProgressIndicator(3, 4)
            Spacer(Modifier.height(48.dp))
            RegisterStoreField(
                stringResource(Res.string.register_store_phone),
                state.phoneNumber,
                stringResource(Res.string.register_store_phone_hint),
                KeyboardType.Phone
            ) { onPhoneNumberChange(it) }
            Spacer(Modifier.height(24.dp))
            RegisterStoreField(
                stringResource(Res.string.register_store_delivery_fee),
                state.deliveryFee,
                stringResource(Res.string.register_store_delivery_fee_hint),
                KeyboardType.Number
            ) { onDeliveryFeeChange(it) }
            Spacer(Modifier.height(24.dp))
            RegisterStoreField(
                stringResource(Res.string.register_store_other_information),
                state.otherInfo,
                stringResource(Res.string.register_store_other_information_hint)
            ) {
                onOtherInfoChange(it)
            }
            Spacer(Modifier.height(32.dp))
            Text(stringResource(Res.string.register_store_operating_time), style = KoinTheme.typography.medium16)
            Spacer(Modifier.height(12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                state.operatingTimes.forEachIndexed { index, operatingTime ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(KoinTheme.colors.neutral0)
                            .noRippleClickable {
                                editingOperatingTimeIndex = index
                                openingTimeInput = operatingTime.openingTime.filter(Char::isDigit)
                                closingTimeInput = operatingTime.closingTime.filter(Char::isDigit)
                                selectedDays = operatingTime.days
                                is24Hours = operatingTime.is24Hours
                                showOperatingTimeDialog = true
                            }.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                operatingTime.days.joinToString(" · ") { weekdayLabels.getValue(it) },
                                style = KoinTheme.typography.medium14
                            )
                            Text(
                                if (operatingTime.is24Hours) {
                                    stringResource(Res.string.register_store_open_24_hours)
                                } else {
                                    "${operatingTime.openingTime}–${operatingTime.closingTime}"
                                },
                                style = KoinTheme.typography.regular13,
                                color = KoinTheme.colors.neutral500
                            )
                        }
                        Text(
                            stringResource(Res.string.common_edit),
                            style = KoinTheme.typography.medium13,
                            color = KoinTheme.colors.primary500
                        )
                    }
                }
                if (state.operatingTimes.isEmpty()) {
                    Text(
                        closedAllDay,
                        style = KoinTheme.typography.regular13,
                        color = KoinTheme.colors.neutral500
                    )
                }
                Button(
                    onClick = {
                        editingOperatingTimeIndex = null
                        openingTimeInput = DEFAULT_OPENING_TIME.filter(Char::isDigit)
                        closingTimeInput = DEFAULT_CLOSING_TIME.filter(Char::isDigit)
                        selectedDays = (RegisterStoreDay.entries - state.operatingTimes.flatMap { it.days })
                            .toImmutableSet()
                        is24Hours = false
                        showOperatingTimeDialog = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = KoinTheme.colors.primary100,
                        contentColor = KoinTheme.colors.primary600
                    )
                ) {
                    Text("${stringResource(Res.string.common_add_symbol)} ${stringResource(Res.string.register_store_operating_time)}")
                }
            }
            Spacer(Modifier.height(32.dp))
            Text(stringResource(Res.string.register_store_available_items), style = KoinTheme.typography.medium16)
            Spacer(Modifier.height(8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                SignupCheckBox(
                    checked = state.isDeliveryAvailable,
                    text = stringResource(Res.string.register_store_delivery_available),
                    onCheckedChange = onDeliveryAvailabilityChange
                )
                SignupCheckBox(
                    checked = state.isCardAvailable,
                    text = stringResource(Res.string.register_store_card_available),
                    onCheckedChange = onCardAvailabilityChange
                )
                SignupCheckBox(
                    checked = state.isBankTransferAvailable,
                    text = stringResource(Res.string.register_store_bank_transfer_available),
                    onCheckedChange = onBankTransferAvailabilityChange
                )
            }
            Spacer(Modifier.weight(1f))
            Spacer(Modifier.height(32.dp))
            Button(
                onClick = onNext,
                enabled = state.isDetailInfoValid,
                modifier = Modifier.fillMaxWidth(),
                shape = KoinTheme.shapes.small,
                colors = primaryButtonColors(),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) { Text(stringResource(Res.string.common_next), style = KoinTheme.typography.medium16) }
        }
    }
}

private const val DEFAULT_OPENING_TIME = "09:00"
private const val DEFAULT_CLOSING_TIME = "22:00"

@Composable
private fun RegisterStoreField(
    title: String,
    value: String,
    hint: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    Text(title, style = KoinTheme.typography.medium16)
    Spacer(Modifier.height(8.dp))
    KoinUnderlineTextField(
        value = value,
        onValueChange = onValueChange,
        hint = hint,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}
