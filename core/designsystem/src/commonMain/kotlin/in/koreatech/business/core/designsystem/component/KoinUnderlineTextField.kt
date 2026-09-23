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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.theme.KoinTheme

@Composable
fun KoinUnderlineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    hint: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textStyle: TextStyle = KoinTheme.typography.regular14,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else 4,
    maxLength: Int = Int.MAX_VALUE,
    title: (@Composable () -> Unit)? = null,
    suffix: (@Composable RowScope.() -> Unit)? = null
) {
    Column(modifier = modifier) {
        title?.let {
            it()
            Spacer(Modifier.height(8.dp))
        }
        BasicTextField(
            value = value,
            onValueChange = { onValueChange(it.take(maxLength)) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = textStyle.copy(color = KoinTheme.colors.neutral800),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            readOnly = readOnly,
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            maxLines = maxLines,
            decorationBox = { innerTextField ->
                Column(modifier = Modifier.width(IntrinsicSize.Min)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(if (singleLine) Modifier.height(44.dp) else Modifier.heightIn(min = 96.dp))
                            .padding(horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            if (value.isEmpty()) {
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
        )
    }
}
