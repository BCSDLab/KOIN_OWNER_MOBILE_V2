package `in`.koreatech.business.core.designsystem.component.user

import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import `in`.koreatech.business.core.designsystem.theme.KoinTheme

@Composable
fun signupButtonColors() =
    ButtonDefaults.buttonColors(
        containerColor = KoinTheme.colors.primary500,
        contentColor = KoinTheme.colors.neutral0,
        disabledContainerColor = KoinTheme.colors.neutral300,
        disabledContentColor = KoinTheme.colors.neutral600
    )
