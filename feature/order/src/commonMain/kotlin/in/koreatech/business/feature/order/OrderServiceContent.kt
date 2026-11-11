package `in`.koreatech.business.feature.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.component.KoinImageThumbnail
import `in`.koreatech.business.core.designsystem.component.KoinSelectableChipGroup
import `in`.koreatech.business.core.designsystem.component.KoinSelectableItem
import `in`.koreatech.business.core.designsystem.component.KoinUnderlineTextField
import `in`.koreatech.business.core.designsystem.component.button.primaryButtonColors
import `in`.koreatech.business.core.designsystem.component.button.secondaryButtonColors
import `in`.koreatech.business.core.designsystem.component.selection.KoinCheckBox
import `in`.koreatech.business.core.designsystem.component.skeleton
import `in`.koreatech.business.core.designsystem.component.textfield.KoinTextFieldAlert
import `in`.koreatech.business.core.designsystem.component.textfield.TextFieldAlertState
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_won
import `in`.koreatech.business.core.designsystem.generated.resources.common_won_format
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_account_holder
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_account_holder_hint
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_account_number
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_account_number_hint
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_bank
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_bank_hint
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_campus_tip
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_delivery_both
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_delivery_campus
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_delivery_off_campus
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_delivery_option
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_delivery_tip_hint
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_document_bank_copy
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_document_business_certificate
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_document_business_license
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_document_error
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_document_image_description
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_document_replace
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_document_required
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_document_upload
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_document_uploaded
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_document_uploading
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_intro_description
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_intro_title
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_menu_empty
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_menu_error
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_menu_guide
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_menu_loading
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_menu_retry
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_minimum_order_amount
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_minimum_order_amount_hint
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_off_campus_tip
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_required
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_section_account
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_section_delivery
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_section_documents
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_section_menu
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_start
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_submit
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_submit_error
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_submit_hint
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_submitted_description
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_submitted_guide
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_submitted_title
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_submitting
import `in`.koreatech.business.core.designsystem.generated.resources.order_service_takeout
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.core.file.rememberImageFilePicker
import `in`.koreatech.business.core.util.CurrencyOutputTransformation
import `in`.koreatech.business.core.util.toCurrencyText
import `in`.koreatech.business.feature.order.model.OrderServiceMenuUiModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.drop
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun OrderServiceContent(
    state: OrderServiceState,
    onFieldChange: (OrderServiceField, String) -> Unit,
    onDeliveryOptionChange: (String) -> Unit,
    onTakeoutChange: (Boolean) -> Unit,
    onMenuToggle: (Int) -> Unit,
    onUploadDocument: (OrderServiceDocument, String, String, ByteArray) -> Unit,
    onFileSelectionFailed: () -> Unit,
    onRetryMenus: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        state.isSubmitted -> OrderServiceSubmittedContent(modifier = modifier)
        else -> OrderServiceFormContent(
            state = state,
            onFieldChange = onFieldChange,
            onDeliveryOptionChange = onDeliveryOptionChange,
            onTakeoutChange = onTakeoutChange,
            onMenuToggle = onMenuToggle,
            onUploadDocument = onUploadDocument,
            onFileSelectionFailed = onFileSelectionFailed,
            onRetryMenus = onRetryMenus,
            onSubmit = onSubmit,
            modifier = modifier
        )
    }
}

@Composable
internal fun OrderServiceIntroContent(
    onStart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(Res.string.order_service_intro_title),
            style = KoinTheme.typography.medium18,
            color = KoinTheme.colors.neutral800
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = stringResource(Res.string.order_service_intro_description),
            style = KoinTheme.typography.regular14,
            color = KoinTheme.colors.neutral600
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onStart,
            modifier = Modifier.fillMaxWidth(),
            shape = KoinTheme.shapes.small,
            colors = primaryButtonColors(),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            Text(stringResource(Res.string.order_service_start), style = KoinTheme.typography.medium16)
        }
    }
}

@Composable
private fun OrderServiceSubmittedContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(Res.string.order_service_submitted_title),
            style = KoinTheme.typography.medium18,
            color = KoinTheme.colors.neutral800
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = stringResource(Res.string.order_service_submitted_description),
            style = KoinTheme.typography.regular14,
            color = KoinTheme.colors.neutral600
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(Res.string.order_service_submitted_guide),
            style = KoinTheme.typography.regular12,
            color = KoinTheme.colors.neutral500
        )
    }
}

@Composable
private fun OrderServiceFormContent(
    state: OrderServiceState,
    onFieldChange: (OrderServiceField, String) -> Unit,
    onDeliveryOptionChange: (String) -> Unit,
    onTakeoutChange: (Boolean) -> Unit,
    onMenuToggle: (Int) -> Unit,
    onUploadDocument: (OrderServiceDocument, String, String, ByteArray) -> Unit,
    onFileSelectionFailed: () -> Unit,
    onRetryMenus: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    var pendingDocument by remember { mutableStateOf<OrderServiceDocument?>(null) }
    val openImagePicker = rememberImageFilePicker(
        onImagePicked = { file ->
            pendingDocument?.let { document ->
                onUploadDocument(document, file.name, file.contentType, file.bytes)
            }
            pendingDocument = null
        },
        onFailure = {
            pendingDocument = null
            onFileSelectionFailed()
        }
    )
    val pickDocument: (OrderServiceDocument) -> Unit = { document ->
        pendingDocument = document
        openImagePicker()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            OrderServiceSection(title = stringResource(Res.string.order_service_section_menu)) {
                Text(
                    text = stringResource(Res.string.order_service_menu_guide),
                    style = KoinTheme.typography.regular12,
                    color = KoinTheme.colors.neutral500
                )
                Spacer(modifier = Modifier.height(12.dp))
                when {
                    state.isLoadingMenus -> OrderServiceMenuLoading()
                    state.hasMenuError -> OrderServiceMenuError(onRetryMenus)
                    state.menus.isEmpty() -> Text(
                        text = stringResource(Res.string.order_service_menu_empty),
                        style = KoinTheme.typography.regular14,
                        color = KoinTheme.colors.neutral600
                    )
                    else -> Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        state.menus.forEach { menu ->
                            OrderServiceMenuRow(
                                menu = menu,
                                checked = menu.id in state.selectedMenuIds,
                                enabled = !state.isSubmitting,
                                onToggle = { onMenuToggle(menu.id) }
                            )
                        }
                    }
                }
            }

            OrderServiceSection(title = stringResource(Res.string.order_service_section_delivery)) {
                ServiceTextField(
                    value = state.minimumOrderAmount,
                    onValueChange = { onFieldChange(OrderServiceField.MINIMUM_ORDER_AMOUNT, it) },
                    placeholder = stringResource(Res.string.order_service_minimum_order_amount_hint),
                    keyboardType = KeyboardType.Number,
                    maxLength = AMOUNT_MAX_LENGTH,
                    formatAsCurrency = true,
                    enabled = !state.isSubmitting,
                    title = stringResource(Res.string.order_service_minimum_order_amount)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(Res.string.order_service_delivery_option),
                    style = KoinTheme.typography.medium14,
                    color = KoinTheme.colors.neutral800
                )
                Spacer(modifier = Modifier.height(8.dp))
                val deliveryOptions = DELIVERY_OPTIONS
                KoinSelectableChipGroup(
                    items = deliveryOptions.mapIndexed { index, option ->
                        KoinSelectableItem(index, stringResource(option.label))
                    }.toImmutableList(),
                    selectedItemId = deliveryOptions.indexOfFirst { it.value == state.deliveryOption }
                        .takeIf { it >= 0 },
                    onItemClick = { index ->
                        if (!state.isSubmitting) {
                            deliveryOptions.getOrNull(index)?.let { onDeliveryOptionChange(it.value) }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                ServiceTextField(
                    value = state.campusDeliveryTip,
                    onValueChange = { onFieldChange(OrderServiceField.CAMPUS_DELIVERY_TIP, it) },
                    placeholder = stringResource(Res.string.order_service_delivery_tip_hint),
                    keyboardType = KeyboardType.Number,
                    maxLength = AMOUNT_MAX_LENGTH,
                    formatAsCurrency = true,
                    enabled = !state.isSubmitting,
                    title = stringResource(Res.string.order_service_campus_tip)
                )
                Spacer(modifier = Modifier.height(16.dp))
                ServiceTextField(
                    value = state.offCampusDeliveryTip,
                    onValueChange = { onFieldChange(OrderServiceField.OFF_CAMPUS_DELIVERY_TIP, it) },
                    placeholder = stringResource(Res.string.order_service_delivery_tip_hint),
                    keyboardType = KeyboardType.Number,
                    maxLength = AMOUNT_MAX_LENGTH,
                    formatAsCurrency = true,
                    enabled = !state.isSubmitting,
                    title = stringResource(Res.string.order_service_off_campus_tip)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.order_service_takeout),
                        modifier = Modifier.weight(1f),
                        style = KoinTheme.typography.medium14,
                        color = KoinTheme.colors.neutral800
                    )
                    Switch(
                        checked = state.isTakeout,
                        onCheckedChange = onTakeoutChange,
                        enabled = !state.isSubmitting,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = KoinTheme.colors.primary500,
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = KoinTheme.colors.neutral300
                        )
                    )
                }
            }

            OrderServiceSection(title = stringResource(Res.string.order_service_section_account)) {
                ServiceTextField(
                    value = state.bank,
                    onValueChange = { onFieldChange(OrderServiceField.BANK, it) },
                    placeholder = stringResource(Res.string.order_service_bank_hint),
                    maxLength = BANK_MAX_LENGTH,
                    showWonSuffix = false,
                    enabled = !state.isSubmitting,
                    title = stringResource(Res.string.order_service_bank)
                )
                Spacer(modifier = Modifier.height(16.dp))
                ServiceTextField(
                    value = state.accountNumber,
                    onValueChange = { onFieldChange(OrderServiceField.ACCOUNT_NUMBER, it) },
                    placeholder = stringResource(Res.string.order_service_account_number_hint),
                    keyboardType = KeyboardType.Number,
                    maxLength = ACCOUNT_NUMBER_MAX_LENGTH,
                    showWonSuffix = false,
                    enabled = !state.isSubmitting,
                    title = stringResource(Res.string.order_service_account_number)
                )
                Spacer(modifier = Modifier.height(16.dp))
                ServiceTextField(
                    value = state.accountHolder,
                    onValueChange = { onFieldChange(OrderServiceField.ACCOUNT_HOLDER, it) },
                    placeholder = stringResource(Res.string.order_service_account_holder_hint),
                    maxLength = ACCOUNT_HOLDER_MAX_LENGTH,
                    showWonSuffix = false,
                    enabled = !state.isSubmitting,
                    title = stringResource(Res.string.order_service_account_holder)
                )
            }

            OrderServiceSection(title = stringResource(Res.string.order_service_section_documents)) {
                Text(
                    text = stringResource(Res.string.order_service_document_required),
                    style = KoinTheme.typography.regular12,
                    color = KoinTheme.colors.neutral500
                )
                Spacer(modifier = Modifier.height(12.dp))
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OrderServiceDocumentRow(
                        label = stringResource(Res.string.order_service_document_business_license),
                        imageUrl = state.businessLicenseUrl,
                        isUploading = OrderServiceDocument.BUSINESS_LICENSE in state.uploadingDocuments,
                        enabled = !state.isSubmitting,
                        onPick = { pickDocument(OrderServiceDocument.BUSINESS_LICENSE) }
                    )
                    OrderServiceDocumentRow(
                        label = stringResource(Res.string.order_service_document_business_certificate),
                        imageUrl = state.businessCertificateUrl,
                        isUploading = OrderServiceDocument.BUSINESS_CERTIFICATE in state.uploadingDocuments,
                        enabled = !state.isSubmitting,
                        onPick = { pickDocument(OrderServiceDocument.BUSINESS_CERTIFICATE) }
                    )
                    OrderServiceDocumentRow(
                        label = stringResource(Res.string.order_service_document_bank_copy),
                        imageUrl = state.bankCopyUrl,
                        isUploading = OrderServiceDocument.BANK_COPY in state.uploadingDocuments,
                        enabled = !state.isSubmitting,
                        onPick = { pickDocument(OrderServiceDocument.BANK_COPY) }
                    )
                }
                if (state.hasUploadError) {
                    Spacer(modifier = Modifier.height(12.dp))
                    KoinTextFieldAlert(
                        text = stringResource(Res.string.order_service_document_error),
                        state = TextFieldAlertState.Error
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            val submitError = state.submitErrorMessage
                ?: stringResource(Res.string.order_service_submit_error).takeIf { state.hasSubmitError }
            if (submitError != null) {
                KoinTextFieldAlert(text = submitError, state = TextFieldAlertState.Error)
                Spacer(modifier = Modifier.height(8.dp))
            }
            if (!state.canSubmit && !state.isSubmitting) {
                Text(
                    text = stringResource(Res.string.order_service_submit_hint),
                    style = KoinTheme.typography.regular12,
                    color = KoinTheme.colors.neutral500
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = state.canSubmit,
                onClick = onSubmit,
                shape = KoinTheme.shapes.small,
                colors = primaryButtonColors(),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                if (state.isSubmitting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = KoinTheme.colors.neutral0,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(stringResource(Res.string.order_service_submitting), style = KoinTheme.typography.medium16)
                } else {
                    Text(stringResource(Res.string.order_service_submit), style = KoinTheme.typography.medium16)
                }
            }
        }
    }
}

@Composable
private fun OrderServiceSection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = KoinTheme.typography.medium16,
            color = KoinTheme.colors.neutral800
        )
        Spacer(modifier = Modifier.height(12.dp))
        content()
    }
}

@Composable
private fun OrderServiceMenuLoading() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(Res.string.order_service_menu_loading),
            style = KoinTheme.typography.regular12,
            color = KoinTheme.colors.neutral500
        )
        repeat(3) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .skeleton(shape = RoundedCornerShape(6.dp))
            )
        }
    }
}

@Composable
private fun OrderServiceMenuError(onRetry: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(Res.string.order_service_menu_error),
            style = KoinTheme.typography.regular14,
            color = KoinTheme.colors.danger600
        )
        Button(
            onClick = onRetry,
            shape = KoinTheme.shapes.small,
            colors = secondaryButtonColors(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(stringResource(Res.string.order_service_menu_retry), style = KoinTheme.typography.medium14)
        }
    }
}

@Composable
private fun OrderServiceMenuRow(
    menu: OrderServiceMenuUiModel,
    checked: Boolean,
    enabled: Boolean,
    onToggle: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        KoinCheckBox(
            checked = checked,
            text = menu.name,
            onCheckedChange = { if (enabled) onToggle() }
        )
        val price = menu.minimumPrice
        if (price != null) {
            Text(
                text = stringResource(Res.string.common_won_format, price.toCurrencyText()),
                modifier = Modifier.padding(start = 24.dp),
                style = KoinTheme.typography.regular12,
                color = KoinTheme.colors.neutral500
            )
        }
    }
}

@Composable
private fun OrderServiceDocumentRow(
    label: String,
    imageUrl: String?,
    isUploading: Boolean,
    enabled: Boolean,
    onPick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (imageUrl != null) {
            KoinImageThumbnail(
                imageUrl = imageUrl,
                contentDescription = stringResource(Res.string.order_service_document_image_description, label),
                modifier = Modifier.size(56.dp)
            )
        } else if (isUploading) {
            Box(Modifier.size(56.dp).skeleton(shape = RoundedCornerShape(8.dp)))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = KoinTheme.typography.medium14,
                color = KoinTheme.colors.neutral800
            )
            Text(
                text = when {
                    isUploading -> stringResource(Res.string.order_service_document_uploading)
                    imageUrl != null -> stringResource(Res.string.order_service_document_uploaded)
                    else -> stringResource(Res.string.order_service_required)
                },
                style = KoinTheme.typography.regular12,
                color = if (imageUrl != null && !isUploading) {
                    KoinTheme.colors.primary600
                } else {
                    KoinTheme.colors.neutral500
                }
            )
        }
        Button(
            enabled = enabled && !isUploading,
            onClick = onPick,
            shape = KoinTheme.shapes.small,
            colors = secondaryButtonColors(),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = stringResource(
                    if (imageUrl != null) {
                        Res.string.order_service_document_replace
                    } else {
                        Res.string.order_service_document_upload
                    }
                ),
                style = KoinTheme.typography.medium13
            )
        }
    }
}

@Composable
private fun ServiceTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    title: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLength: Int = Int.MAX_VALUE,
    formatAsCurrency: Boolean = false,
    showWonSuffix: Boolean = true,
    enabled: Boolean = true
) {
    val textFieldState = remember { TextFieldState(value) }
    val currentOnValueChange by rememberUpdatedState(onValueChange)
    val currentValue by rememberUpdatedState(value)
    val outputTransformation = remember(formatAsCurrency) {
        if (formatAsCurrency) CurrencyOutputTransformation() else null
    }
    LaunchedEffect(value) {
        if (textFieldState.text.toString() != value) {
            textFieldState.setTextAndPlaceCursorAtEnd(value)
        }
    }
    val titleContent: (@Composable () -> Unit)? = title?.let { titleText ->
        {
            Text(
                text = titleText,
                style = KoinTheme.typography.medium15,
                color = KoinTheme.colors.neutral800
            )
        }
    }
    val suffix: (@Composable RowScope.() -> Unit)? = if (showWonSuffix) {
        {
            Text(
                text = stringResource(Res.string.common_won),
                style = KoinTheme.typography.regular14,
                color = KoinTheme.colors.neutral600
            )
        }
    } else {
        null
    }
    LaunchedEffect(textFieldState) {
        snapshotFlow { textFieldState.text.toString() }
            .drop(1)
            .collect { text -> if (text != currentValue) currentOnValueChange(text) }
    }
    KoinUnderlineTextField(
        state = textFieldState,
        modifier = Modifier.fillMaxWidth(),
        placeholder = placeholder,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        outputTransformation = outputTransformation,
        readOnly = !enabled,
        maxLength = maxLength,
        title = titleContent,
        suffix = suffix
    )
}

private data class DeliveryOption(
    val value: String,
    val label: StringResource
)

private val DELIVERY_OPTIONS = listOf(
    DeliveryOption("CAMPUS", Res.string.order_service_delivery_campus),
    DeliveryOption("OFF_CAMPUS", Res.string.order_service_delivery_off_campus),
    DeliveryOption("BOTH", Res.string.order_service_delivery_both)
)

private const val AMOUNT_MAX_LENGTH = 9
private const val BANK_MAX_LENGTH = 10
private const val ACCOUNT_NUMBER_MAX_LENGTH = 20
private const val ACCOUNT_HOLDER_MAX_LENGTH = 50
