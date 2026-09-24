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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.component.KoinImagePreview
import `in`.koreatech.business.core.designsystem.component.KoinUnderlineTextField
import `in`.koreatech.business.core.designsystem.component.button.primaryButtonColors
import `in`.koreatech.business.core.designsystem.component.progress.KoinProgressHeader
import `in`.koreatech.business.core.designsystem.component.progress.KoinProgressIndicator
import `in`.koreatech.business.core.designsystem.component.selection.KoinCheckBox
import `in`.koreatech.business.core.designsystem.component.topbar.KoinTopAppBar
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_add_symbol
import `in`.koreatech.business.core.designsystem.generated.resources.common_cancel
import `in`.koreatech.business.core.designsystem.generated.resources.common_edit
import `in`.koreatech.business.core.designsystem.generated.resources.common_next
import `in`.koreatech.business.core.designsystem.generated.resources.common_won
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_address
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_address_hint
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_address_search
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_address_search_button
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_address_search_empty
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_address_search_error
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_address_search_hint
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
import `in`.koreatech.business.core.util.CurrencyVisualTransformation
import `in`.koreatech.business.core.util.KRPhoneNumberVisualTransformation
import `in`.koreatech.business.feature.store.register.model.RegisterStoreAddress
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
    onAddressSelect: (String) -> Unit,
    onAddressSearch: (String) -> Unit,
    onUploadImage: (String, String, ByteArray) -> Unit,
    onImageSelectionFailed: () -> Unit,
    onRemoveImage: (Int) -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddressSearchDialog by remember { mutableStateOf(false) }
    val openImagePicker = rememberImageFilePicker(
        onImagePicked = { onUploadImage(it.name, it.contentType, it.bytes) },
        onFailure = { onImageSelectionFailed() }
    )
    if (showAddressSearchDialog) {
        RegisterStoreAddressSearchDialog(
            results = state.addressSearchResults,
            hasSearchResult = state.hasAddressSearchResult,
            isSearching = state.isAddressSearching,
            isError = state.isAddressSearchError,
            onSearch = onAddressSearch,
            onSelect = {
                onAddressSelect(it.displayAddress)
                showAddressSearchDialog = false
            },
            onDismiss = { showAddressSearchDialog = false }
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
            KoinProgressHeader(stringResource(Res.string.register_store_basic_step), 2, 4)
            Spacer(Modifier.height(8.dp))
            KoinProgressIndicator(2, 4)
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
                stringResource(Res.string.register_store_address_hint),
                readOnly = true,
                suffix = {
                    TextButton(onClick = { showAddressSearchDialog = true }) {
                        Text(stringResource(Res.string.register_store_address_search_button))
                    }
                },
                onValueChange = {}
            )
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
private fun RegisterStoreAddressSearchDialog(
    results: ImmutableList<RegisterStoreAddress>,
    hasSearchResult: Boolean,
    isSearching: Boolean,
    isError: Boolean,
    onSearch: (String) -> Unit,
    onSelect: (RegisterStoreAddress) -> Unit,
    onDismiss: () -> Unit
) {
    var keyword by remember { mutableStateOf("") }
    val search = { onSearch(keyword) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(Res.string.register_store_address_search), style = KoinTheme.typography.medium18) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                KoinUnderlineTextField(
                    value = keyword,
                    onValueChange = { keyword = it },
                    hint = stringResource(Res.string.register_store_address_search_hint),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { search() }),
                    suffix = {
                        TextButton(onClick = search, enabled = keyword.isNotBlank() && !isSearching) {
                            Text(stringResource(Res.string.register_store_address_search_button))
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                when {
                    isSearching -> Box(
                        modifier = Modifier.fillMaxWidth().height(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(32.dp))
                    }
                    isError -> AddressSearchMessage(stringResource(Res.string.register_store_address_search_error))
                    hasSearchResult && results.isEmpty() ->
                        AddressSearchMessage(stringResource(Res.string.register_store_address_search_empty))
                    else -> LazyColumn(modifier = Modifier.fillMaxWidth().heightIn(max = 320.dp)) {
                        items(
                            items = results,
                            key = { "${it.zipCode}-${it.roadAddress}-${it.jibunAddress}" }
                        ) { address ->
                            AddressSearchItem(address = address, onClick = { onSelect(address) })
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(Res.string.common_cancel)) }
        }
    )
}

@Composable
private fun AddressSearchMessage(message: String) {
    Box(
        modifier = Modifier.fillMaxWidth().height(120.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(message, style = KoinTheme.typography.regular14, color = KoinTheme.colors.neutral500)
    }
}

@Composable
private fun AddressSearchItem(
    address: RegisterStoreAddress,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().noRippleClickable { onClick() }.padding(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = address.buildingName.ifBlank { address.displayAddress },
            style = KoinTheme.typography.medium14,
            color = KoinTheme.colors.neutral800
        )
        if (address.buildingName.isNotBlank()) {
            Text(address.displayAddress, style = KoinTheme.typography.regular13, color = KoinTheme.colors.neutral600)
        }
        if (address.jibunAddress.isNotBlank() && address.jibunAddress != address.displayAddress) {
            Text(address.jibunAddress, style = KoinTheme.typography.regular13, color = KoinTheme.colors.neutral500)
        }
    }
    HorizontalDivider(color = KoinTheme.colors.neutral200)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
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
    var pickingTime by remember { mutableStateOf<OperatingTimeField?>(null) }

    pickingTime?.let { field ->
        RegisterStoreTimePickerDialog(
            initialTime = if (field == OperatingTimeField.Opening) openingTimeInput else closingTimeInput,
            onDismiss = { pickingTime = null },
            onConfirm = { selectedTime ->
                if (field == OperatingTimeField.Opening) openingTimeInput = selectedTime else closingTimeInput = selectedTime
                pickingTime = null
            }
        )
    }

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
                        Text(
                            text = openingTimeInput.toTimeText(),
                            modifier = Modifier.fillMaxWidth().noRippleClickable { pickingTime = OperatingTimeField.Opening }.padding(12.dp),
                            style = KoinTheme.typography.regular14
                        )
                        Text(stringResource(Res.string.register_store_end_time), style = KoinTheme.typography.medium14)
                        Text(
                            text = closingTimeInput.toTimeText(),
                            modifier = Modifier.fillMaxWidth().noRippleClickable { pickingTime = OperatingTimeField.Closing }.padding(12.dp),
                            style = KoinTheme.typography.regular14
                        )
                    }
                    KoinCheckBox(
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
            KoinProgressHeader(stringResource(Res.string.register_store_operation_step), 3, 4)
            Spacer(Modifier.height(8.dp))
            KoinProgressIndicator(3, 4)
            Spacer(Modifier.height(48.dp))
            RegisterStoreField(
                stringResource(Res.string.register_store_phone),
                state.phoneNumber,
                stringResource(Res.string.register_store_phone_hint),
                KeyboardType.Phone,
                onValueChange = { onPhoneNumberChange(it) },
                maxLength = 11,
                visualTransformation = KRPhoneNumberVisualTransformation()
            )
            Spacer(Modifier.height(24.dp))
            RegisterStoreField(
                stringResource(Res.string.register_store_delivery_fee),
                state.deliveryFee,
                stringResource(Res.string.register_store_delivery_fee_hint),
                KeyboardType.Number,
                visualTransformation = CurrencyVisualTransformation(),
                suffix = {
                    Text(
                        text = stringResource(Res.string.common_won),
                        style = KoinTheme.typography.regular14,
                        color = KoinTheme.colors.neutral600
                    )
                }
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
                KoinCheckBox(
                    checked = state.isDeliveryAvailable,
                    text = stringResource(Res.string.register_store_delivery_available),
                    onCheckedChange = onDeliveryAvailabilityChange
                )
                KoinCheckBox(
                    checked = state.isCardAvailable,
                    text = stringResource(Res.string.register_store_card_available),
                    onCheckedChange = onCardAvailabilityChange
                )
                KoinCheckBox(
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

private enum class OperatingTimeField { Opening, Closing }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterStoreTimePickerDialog(
    initialTime: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    val timePickerState = rememberTimePickerState(
        initialHour = initialTime.take(2).toIntOrNull() ?: 9,
        initialMinute = initialTime.drop(2).take(2).toIntOrNull() ?: 0
    )
    AlertDialog(
        onDismissRequest = onDismiss,
        text = { TimePicker(state = timePickerState) },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(timePickerState.hour.toTwoDigitString() + timePickerState.minute.toTwoDigitString())
                }
            ) { Text(stringResource(Res.string.register_store_apply)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(Res.string.common_cancel)) }
        }
    )
}

private fun Int.toTwoDigitString(): String = toString().padStart(2, '0')

@Composable
private fun RegisterStoreField(
    title: String,
    value: String,
    hint: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLength: Int = Int.MAX_VALUE,
    readOnly: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    suffix: (@Composable androidx.compose.foundation.layout.RowScope.() -> Unit)? = null,
    onValueChange: (String) -> Unit
) {
    Text(title, style = KoinTheme.typography.medium16)
    Spacer(Modifier.height(8.dp))
    KoinUnderlineTextField(
        value = value,
        onValueChange = onValueChange,
        hint = hint,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        maxLength = maxLength,
        readOnly = readOnly,
        visualTransformation = visualTransformation,
        suffix = suffix
    )
}
