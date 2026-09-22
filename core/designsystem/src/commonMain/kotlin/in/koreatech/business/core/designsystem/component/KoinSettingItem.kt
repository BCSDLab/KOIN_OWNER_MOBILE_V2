package `in`.koreatech.business.core.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.noRippleClickable
import `in`.koreatech.business.core.designsystem.theme.KoinTheme

@Composable
fun KoinSettingItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    trailingText: String? = null
) {
    Row(
        modifier = modifier.fillMaxWidth().noRippleClickable(onClick = onClick).padding(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = KoinTheme.typography.regular16,
            color = KoinTheme.colors.neutral800,
            modifier = Modifier.weight(1f)
        )
        trailingText?.let {
            Text(it, style = KoinTheme.typography.regular14, color = KoinTheme.colors.neutral500)
        }
    }
}
