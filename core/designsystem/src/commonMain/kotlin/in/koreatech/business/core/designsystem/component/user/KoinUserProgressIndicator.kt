package `in`.koreatech.business.core.designsystem.component.user

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.theme.KoinTheme

@Composable
fun KoinUserProgressIndicator(
    currentStep: Int,
    maxStep: Int
) {
    val animatedStep = remember { Animatable((currentStep - 1).toFloat()) }
    val backgroundColor = KoinTheme.colors.neutral400
    val stepColor = KoinTheme.colors.primary500
    LaunchedEffect(Unit) { animatedStep.animateTo(currentStep.toFloat()) }
    Canvas(modifier = Modifier.fillMaxWidth()) {
        drawLine(backgroundColor, Offset.Zero, Offset(size.width, size.height), 4.dp.toPx(), StrokeCap.Round)
        drawLine(stepColor, Offset.Zero, Offset(size.width / maxStep * animatedStep.value, size.height), 4.dp.toPx(), StrokeCap.Round)
    }
}
