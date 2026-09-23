package `in`.koreatech.business.core.designsystem.component.progress

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_progress_count
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_progress_title
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun KoinProgressHeader(
    text: String,
    currentStep: Int,
    maxStep: Int
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(Res.string.sign_up_progress_title, currentStep, text),
            style = KoinTheme.typography.medium16,
            color = KoinTheme.colors.primary500
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(Res.string.sign_up_progress_count, currentStep, maxStep),
            style = KoinTheme.typography.medium16,
            color = KoinTheme.colors.primary500
        )
    }
}
