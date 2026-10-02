package `in`.koreatech.business.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldDecorator
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.then
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.theme.KoinTheme

@Composable
fun KoinUnderlineTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    hint: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    outputTransformation: OutputTransformation? = null,
    textStyle: TextStyle = KoinTheme.typography.regular14,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else 4,
    maxLength: Int = Int.MAX_VALUE,
    title: (@Composable () -> Unit)? = null,
    suffix: (@Composable RowScope.() -> Unit)? = null
) {
    val inputTransformation = remember(maxLength, keyboardOptions.keyboardType) {
        inputTransformation(maxLength, keyboardOptions.keyboardType)
    }

    Column(modifier = modifier) {
        title?.let {
            it()
            Spacer(Modifier.height(8.dp))
        }
        val decorator = TextFieldDecorator { innerTextField ->
            Column(modifier = Modifier.width(IntrinsicSize.Min)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(if (singleLine) Modifier.height(44.dp) else Modifier.heightIn(min = 96.dp))
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (state.text.isEmpty()) {
                            Text(
                                hint ?: placeholder,
                                style = textStyle,
                                color = KoinTheme.colors.neutral400
                            )
                        }
                        innerTextField()
                    }
                    suffix?.invoke(this)
                }
                HorizontalDivider(color = KoinTheme.colors.neutral400)
            }
        }
        BasicTextField(
            state = state,
            modifier = Modifier.fillMaxWidth(),
            textStyle = textStyle.copy(color = KoinTheme.colors.neutral800),
            keyboardOptions = keyboardOptions,
            onKeyboardAction = onKeyboardAction,
            inputTransformation = inputTransformation,
            outputTransformation = outputTransformation,
            readOnly = readOnly,
            lineLimits = if (singleLine) {
                TextFieldLineLimits.SingleLine
            } else {
                TextFieldLineLimits.MultiLine(maxHeightInLines = maxLines)
            },
            decorator = decorator
        )
    }
}

private fun inputTransformation(
    maxLength: Int,
    keyboardType: KeyboardType
): InputTransformation? {
    val digitsOnly = DIGITS_ONLY_INPUT_TRANSFORMATION.takeIf { keyboardType == KeyboardType.Number }
    val lengthLimit = InputTransformation.maxLength(maxLength).takeIf { maxLength != Int.MAX_VALUE }
    return when {
        digitsOnly != null && lengthLimit != null -> digitsOnly.then(lengthLimit)
        digitsOnly != null -> digitsOnly
        else -> lengthLimit
    }
}

private val DIGITS_ONLY_INPUT_TRANSFORMATION = InputTransformation {
    for (index in length - 1 downTo 0) {
        if (!charAt(index).isDigit()) replace(index, index + 1, "")
    }
}
