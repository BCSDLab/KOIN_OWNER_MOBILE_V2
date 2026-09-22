package `in`.koreatech.business.feature.signup.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.theme.KoinTheme

@Composable
fun SignupTerm(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(KoinTheme.colors.neutral0, KoinTheme.shapes.extraSmall)
            .verticalScroll(rememberScrollState())
            .padding(12.dp)
    ) {
        Text(text, style = KoinTheme.typography.regular10)
    }
}
