package `in`.koreatech.business.core.designsystem.component.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.ic_checkbox_checked
import `in`.koreatech.business.core.designsystem.generated.resources.ic_checkbox_unchecked
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import org.jetbrains.compose.resources.painterResource

@Composable
fun SignupCheckBox(
    checked: Boolean,
    text: String,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Image(
            painterResource(
                if (checked) Res.drawable.ic_checkbox_checked else Res.drawable.ic_checkbox_unchecked
            ),
            text,
            modifier =
            Modifier.clickable {
                onCheckedChange(!checked)
            }
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text,
            color = Color(0xFF8E8E8E),
            style = KoinTheme.typography.medium14,
            modifier = Modifier.clickable { onCheckedChange(!checked) }
        )
    }
}
