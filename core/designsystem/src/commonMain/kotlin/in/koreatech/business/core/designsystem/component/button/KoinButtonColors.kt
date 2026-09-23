package `in`.koreatech.business.core.designsystem.component.button

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import `in`.koreatech.business.core.designsystem.theme.KoinTheme

@Composable
fun primaryButtonColors() = ButtonDefaults.buttonColors(
    containerColor = KoinTheme.colors.primary500,
    contentColor = KoinTheme.colors.neutral0,
    disabledContainerColor = KoinTheme.colors.neutral200,
    disabledContentColor = KoinTheme.colors.neutral600
)

@Composable
fun secondaryButtonColors() = ButtonDefaults.buttonColors(
    containerColor = KoinTheme.colors.primary100,
    contentColor = KoinTheme.colors.primary600,
    disabledContainerColor = KoinTheme.colors.neutral200,
    disabledContentColor = KoinTheme.colors.neutral600
)

@Preview
@Composable
fun PrimaryButtonPreview() {
    Button(
        onClick = {},
        colors = primaryButtonColors()
    ) {
        Text(
            text = "Primary Button",
            style = KoinTheme.typography.medium16
        )
    }
}

@Preview
@Composable
fun SecondaryButtonPreview() {
    Button(
        onClick = {},
        colors = secondaryButtonColors()
    ) {
        Text(
            text = "Secondary Button",
            style = KoinTheme.typography.regular14
        )
    }
}
