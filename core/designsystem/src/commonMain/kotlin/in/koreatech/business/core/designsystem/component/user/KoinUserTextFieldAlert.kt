package `in`.koreatech.business.core.designsystem.component.user

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.theme.KoinTheme

enum class AlertState { Error, Warning, Success }

@Composable
fun KoinUserTextFieldAlert(
    text: String,
    state: AlertState
) {
    val color =
        when (state) {
            AlertState.Error -> KoinTheme.colors.danger600
            AlertState.Warning -> KoinTheme.colors.primary600
            AlertState.Success -> KoinTheme.colors.success700
        }
    Text(text = text, modifier = Modifier.padding(horizontal = 4.dp), style = KoinTheme.typography.regular12, color = color)
}
