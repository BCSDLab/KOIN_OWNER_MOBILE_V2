package `in`.koreatech.business.core.designsystem.component.textfield

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.theme.KoinTheme

enum class TextFieldAlertState { Error, Warning, Success }

@Composable
fun KoinTextFieldAlert(
    text: String,
    state: TextFieldAlertState
) {
    val color = when (state) {
        TextFieldAlertState.Error -> KoinTheme.colors.danger600
        TextFieldAlertState.Warning -> KoinTheme.colors.primary600
        TextFieldAlertState.Success -> KoinTheme.colors.success700
    }
    Text(text = text, modifier = Modifier.padding(horizontal = 4.dp), style = KoinTheme.typography.regular12, color = color)
}
