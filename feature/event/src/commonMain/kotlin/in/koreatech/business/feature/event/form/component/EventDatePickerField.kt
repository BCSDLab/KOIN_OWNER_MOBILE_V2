package `in`.koreatech.business.feature.event.form.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_cancel
import `in`.koreatech.business.core.designsystem.generated.resources.common_confirm
import `in`.koreatech.business.core.designsystem.noRippleClickable
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.feature.event.util.dateToEpochMillis
import `in`.koreatech.business.feature.event.util.epochMillisToDate
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventDatePickerField(
    title: String,
    value: String,
    placeholder: String,
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    minimumDate: String? = null
) {
    var showDatePicker by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        Text(
            text = title,
            style = KoinTheme.typography.medium15,
            color = KoinTheme.colors.neutral800
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .border(
                    width = 1.dp,
                    color = KoinTheme.colors.neutral400,
                    shape = RoundedCornerShape(8.dp)
                ).noRippleClickable { showDatePicker = true }
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = value.ifEmpty { placeholder },
                style = KoinTheme.typography.regular14,
                color = if (value.isEmpty()) {
                    KoinTheme.colors.neutral400
                } else {
                    KoinTheme.colors.neutral800
                }
            )
        }
    }
    if (showDatePicker) {
        val minimumDateMillis = minimumDate?.let(::dateToEpochMillis)
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = dateToEpochMillis(value),
            selectableDates = remember(minimumDateMillis) {
                object : SelectableDates {
                    override fun isSelectableDate(utcTimeMillis: Long): Boolean = minimumDateMillis == null || utcTimeMillis >= minimumDateMillis
                }
            }
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    enabled = datePickerState.selectedDateMillis != null,
                    onClick = {
                        datePickerState.selectedDateMillis?.let { selectedDateMillis ->
                            onDateSelected(epochMillisToDate(selectedDateMillis))
                        }
                        showDatePicker = false
                    }
                ) {
                    Text(text = stringResource(Res.string.common_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(text = stringResource(Res.string.common_cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
