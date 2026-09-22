package `in`.koreatech.business.core.designsystem.component

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import `in`.koreatech.business.core.designsystem.theme.KoinTheme

@Composable
fun KoinScreenTitle(
    text: String,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal),
    color: Color = KoinTheme.colors.neutral800
) {
    Text(
        text = text,
        modifier = modifier.windowInsetsPadding(windowInsets),
        style = KoinTheme.typography.bold20,
        color = color
    )
}
