package `in`.koreatech.business.feature.signup.complete

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.component.topbar.KoinTopAppBar
import `in`.koreatech.business.core.designsystem.component.user.signupButtonColors
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_koin
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_complete
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_go_to_login
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_go_to_start
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_title
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SignUpCompleteScreen(
    onBack: () -> Unit,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            KoinTopAppBar(
                title = { Text(stringResource(Res.string.sign_up_title), style = KoinTheme.typography.medium18) },
                onNavigationIconClick = onBack
            )
        },
        containerColor = KoinTheme.colors.neutral75
    ) { contentPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(contentPadding).padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(Res.string.common_koin),
                color = KoinTheme.colors.primary500,
                style = KoinTheme.typography.bold20
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = stringResource(Res.string.sign_up_complete), style = KoinTheme.typography.medium18)
            Spacer(modifier = Modifier.height(60.dp))
            Button(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                onClick = onComplete,
                shape = KoinTheme.shapes.small,
                colors = signupButtonColors(),
                contentPadding = PaddingValues(12.dp)
            ) { Text(stringResource(Res.string.sign_up_go_to_login), style = KoinTheme.typography.medium15) }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                onClick = onComplete,
                shape = KoinTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(containerColor = KoinTheme.colors.neutral0),
                border = BorderStroke(1.dp, KoinTheme.colors.primary500),
                contentPadding = PaddingValues(12.dp)
            ) {
                Text(text = stringResource(Res.string.sign_up_go_to_start), color = KoinTheme.colors.primary500)
            }
        }
    }
}
