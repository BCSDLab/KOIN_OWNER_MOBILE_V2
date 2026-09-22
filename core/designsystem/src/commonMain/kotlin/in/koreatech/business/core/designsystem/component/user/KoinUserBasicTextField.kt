package `in`.koreatech.business.core.designsystem.component.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.theme.KoinTheme

@Composable
fun KoinUserBasicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    hint: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it.trim()) },
        keyboardOptions = keyboardOptions,
        textStyle = KoinTheme.typography.regular14,
        singleLine = true,
        visualTransformation = visualTransformation,
        decorationBox = { innerTextField ->
            Column(modifier = Modifier.width(IntrinsicSize.Min)) {
                Box(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 4.dp)) {
                    if (value.isEmpty()) Text(text = hint, style = KoinTheme.typography.regular14, color = KoinTheme.colors.neutral400)
                    innerTextField()
                }
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
            }
        }
    )
}
