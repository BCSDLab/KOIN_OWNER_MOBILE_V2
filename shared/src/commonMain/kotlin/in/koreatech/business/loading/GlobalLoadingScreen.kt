package `in`.koreatech.business.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import `in`.koreatech.business.core.designsystem.theme.KoinTheme

@Composable
fun GlobalLoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(KoinTheme.colors.neutral75),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = KoinTheme.colors.primary500)
    }
}
