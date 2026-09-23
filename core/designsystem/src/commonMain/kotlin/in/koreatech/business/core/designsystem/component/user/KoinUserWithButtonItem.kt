package `in`.koreatech.business.core.designsystem.component.user

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.component.button.primaryButtonColors
import `in`.koreatech.business.core.designsystem.theme.KoinTheme

@Composable
fun KoinUserWithButtonItem(
    value: String,
    hint: String,
    buttonText: String,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    buttonEnabled: Boolean,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max), verticalAlignment = Alignment.CenterVertically) {
        KoinUserBasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            hint = hint,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Done),
            visualTransformation = visualTransformation
        )
        Spacer(modifier = Modifier.width(16.dp))
        CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
            Button(
                modifier = Modifier.widthIn(min = 86.dp),
                onClick = onButtonClick,
                enabled = buttonEnabled,
                shape = KoinTheme.shapes.extraSmall,
                colors = primaryButtonColors(),
                contentPadding = PaddingValues(vertical = 6.dp, horizontal = 12.dp)
            ) { Text(buttonText, style = KoinTheme.typography.regular10) }
        }
    }
}
