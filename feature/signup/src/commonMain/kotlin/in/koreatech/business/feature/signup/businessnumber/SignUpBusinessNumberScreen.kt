package `in`.koreatech.business.feature.signup.businessnumber

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.component.topbar.KoinTopAppBar
import `in`.koreatech.business.core.designsystem.component.user.KoinUserBasicTextField
import `in`.koreatech.business.core.designsystem.component.user.KoinUserProgressHeader
import `in`.koreatech.business.core.designsystem.component.user.KoinUserProgressIndicator
import `in`.koreatech.business.core.designsystem.component.button.primaryButtonColors
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_next
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_business_hint
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_business_input
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_business_step
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_title
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.core.util.BusinessNumberVisualTransformation
import `in`.koreatech.business.feature.signup.SignupState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SignUpBusinessNumberScreen(
    state: SignupState,
    onBack: () -> Unit,
    onBusinessNumberChange: (String) -> Unit,
    navigateToNextScreen: () -> Unit,
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
            modifier =
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
        ) {
            KoinUserProgressHeader(stringResource(Res.string.sign_up_business_step), 4, 6)
            Spacer(modifier = Modifier.height(8.dp))
            KoinUserProgressIndicator(4, 6)
            Spacer(modifier = Modifier.height(64.dp))
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(text = stringResource(Res.string.sign_up_business_input), style = KoinTheme.typography.medium16)
                Spacer(modifier = Modifier.height(16.dp))
                KoinUserBasicTextField(
                    value = state.businessNumber,
                    onValueChange = onBusinessNumberChange,
                    hint = stringResource(Res.string.sign_up_business_hint),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                    visualTransformation = BusinessNumberVisualTransformation()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = navigateToNextScreen,
                enabled = state.businessNumber.isNotBlank(),
                shape = KoinTheme.shapes.small,
                colors = primaryButtonColors(),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) { Text(stringResource(Res.string.common_next), style = KoinTheme.typography.medium16) }
        }
    }
}
