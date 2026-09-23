package `in`.koreatech.business.feature.signup.password

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
import `in`.koreatech.business.core.designsystem.component.KoinUnderlineTextField
import `in`.koreatech.business.core.designsystem.component.textfield.KoinPasswordTextField
import `in`.koreatech.business.core.designsystem.component.button.primaryButtonColors
import `in`.koreatech.business.core.designsystem.component.topbar.KoinTopAppBar
import `in`.koreatech.business.core.designsystem.component.textfield.TextFieldAlertState
import `in`.koreatech.business.core.designsystem.component.progress.KoinProgressHeader
import `in`.koreatech.business.core.designsystem.component.progress.KoinProgressIndicator
import `in`.koreatech.business.core.designsystem.component.textfield.KoinTextFieldAlert
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_next
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_password_confirm_hint
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_password_hint
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_password_input
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_password_match
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_password_mismatch
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_password_rule
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_password_step
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_title
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.feature.signup.SignupState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SignUpPasswordScreen(
    state: SignupState,
    onBack: () -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmChange: (String) -> Unit,
    navigateToNextScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isPasswordValid = state.password.length in 6..18
    val isPasswordEqual = state.password == state.passwordConfirmation
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
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
        ) {
            KoinProgressHeader(text = stringResource(Res.string.sign_up_password_step), currentStep = 3, maxStep = 6)
            Spacer(modifier = Modifier.height(8.dp))
            KoinProgressIndicator(currentStep = 3, maxStep = 6)
            Spacer(modifier = Modifier.height(64.dp))
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(text = stringResource(Res.string.sign_up_password_input), style = KoinTheme.typography.medium16)
                Spacer(modifier = Modifier.height(16.dp))
                KoinPasswordTextField(
                    value = state.password,
                    onValueChange = onPasswordChange,
                    hint = stringResource(Res.string.sign_up_password_hint),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next)
                )
                if (state.password.isNotEmpty() && !isPasswordValid) {
                    KoinTextFieldAlert(stringResource(Res.string.sign_up_password_rule), TextFieldAlertState.Warning)
                }
                if (isPasswordValid) {
                    Spacer(modifier = Modifier.height(16.dp))
                    KoinPasswordTextField(
                        value = state.passwordConfirmation,
                        onValueChange = onPasswordConfirmChange,
                        hint = stringResource(Res.string.sign_up_password_confirm_hint),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)
                    )
                }
                if (isPasswordValid && state.passwordConfirmation.isNotEmpty()) {
                    KoinTextFieldAlert(
                        text = stringResource(
                            if (isPasswordEqual) Res.string.sign_up_password_match else Res.string.sign_up_password_mismatch
                        ),
                        state = if (isPasswordEqual) TextFieldAlertState.Success else TextFieldAlertState.Warning
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = navigateToNextScreen,
                enabled = isPasswordValid && isPasswordEqual,
                shape = KoinTheme.shapes.small,
                colors = primaryButtonColors(),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) { Text(stringResource(Res.string.common_next), style = KoinTheme.typography.medium16) }
        }
    }
}
