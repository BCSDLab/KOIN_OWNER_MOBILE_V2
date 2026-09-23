package `in`.koreatech.business.feature.signin.password

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.zacsweers.metrox.viewmodel.metroViewModel
import `in`.koreatech.business.core.designsystem.component.KoinUnderlineTextField
import `in`.koreatech.business.core.designsystem.component.textfield.KoinPasswordTextField
import `in`.koreatech.business.core.designsystem.component.button.primaryButtonColors
import `in`.koreatech.business.core.designsystem.component.topbar.KoinTopAppBar
import `in`.koreatech.business.core.designsystem.component.textfield.TextFieldAlertState
import `in`.koreatech.business.core.designsystem.component.textfield.KoinTextFieldAlert
import `in`.koreatech.business.core.designsystem.component.textfield.KoinTextFieldWithButton
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_confirm
import `in`.koreatech.business.core.designsystem.generated.resources.password_reset_complete
import `in`.koreatech.business.core.designsystem.generated.resources.password_reset_error_code
import `in`.koreatech.business.core.designsystem.generated.resources.password_reset_error_password
import `in`.koreatech.business.core.designsystem.generated.resources.password_reset_error_phone
import `in`.koreatech.business.core.designsystem.generated.resources.password_reset_error_reset
import `in`.koreatech.business.core.designsystem.generated.resources.password_reset_error_send
import `in`.koreatech.business.core.designsystem.generated.resources.password_reset_go_sign_in
import `in`.koreatech.business.core.designsystem.generated.resources.password_reset_password_description
import `in`.koreatech.business.core.designsystem.generated.resources.password_reset_submit
import `in`.koreatech.business.core.designsystem.generated.resources.password_reset_title
import `in`.koreatech.business.core.designsystem.generated.resources.password_reset_verification_description
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_password_confirm_hint
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_password_hint
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_phone_hint
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_verification_hint
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_verification_resend
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_verification_send
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import org.jetbrains.compose.resources.stringResource
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun PasswordResetScreen(
    onBack: () -> Unit,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PasswordResetViewModel = metroViewModel()
) {
    val state by viewModel.collectAsState()

    PasswordResetScreenImpl(
        state = state,
        onBack = onBack,
        onComplete = onComplete,
        onPhoneNumberChange = viewModel::updatePhoneNumber,
        onCodeChange = viewModel::updateCode,
        onSendCode = viewModel::sendCode,
        onVerifyCode = viewModel::verifyCode,
        onPasswordChange = viewModel::updatePassword,
        onPasswordConfirmationChange = viewModel::updatePasswordConfirmation,
        onReset = viewModel::resetPassword,
        modifier = modifier
    )
}

@Composable
private fun PasswordResetScreenImpl(
    state: PasswordResetState,
    onBack: () -> Unit,
    onComplete: () -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onCodeChange: (String) -> Unit,
    onSendCode: () -> Unit,
    onVerifyCode: () -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmationChange: (String) -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            KoinTopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.password_reset_title),
                        style = KoinTheme.typography.medium18
                    )
                },
                onNavigationIconClick = onBack
            )
        },
        containerColor = KoinTheme.colors.neutral75
    ) { paddingValues ->
        when (state.step) {
            PasswordResetStep.Verification ->
                PasswordResetVerificationContent(
                    state = state,
                    onPhoneNumberChange = onPhoneNumberChange,
                    onCodeChange = onCodeChange,
                    onSendCode = onSendCode,
                    onVerifyCode = onVerifyCode,
                    modifier = Modifier.padding(paddingValues)
                )
            PasswordResetStep.Password ->
                PasswordResetPasswordContent(
                    state = state,
                    onPasswordChange = onPasswordChange,
                    onPasswordConfirmationChange = onPasswordConfirmationChange,
                    onReset = onReset,
                    modifier = Modifier.padding(paddingValues)
                )
            PasswordResetStep.Complete ->
                PasswordResetCompleteContent(
                    onComplete = onComplete,
                    modifier = Modifier.padding(paddingValues)
                )
        }
    }
}

@Composable
private fun PasswordResetVerificationContent(
    state: PasswordResetState,
    onPhoneNumberChange: (String) -> Unit,
    onCodeChange: (String) -> Unit,
    onSendCode: () -> Unit,
    onVerifyCode: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = stringResource(Res.string.password_reset_verification_description),
            style = KoinTheme.typography.regular15,
            color = KoinTheme.colors.neutral600
        )
        KoinTextFieldWithButton(
            value = state.phoneNumber,
            hint = stringResource(Res.string.sign_up_phone_hint),
            buttonText = stringResource(
                if (state.isCodeSent) {
                    Res.string.sign_up_verification_resend
                } else {
                    Res.string.sign_up_verification_send
                }
            ),
            keyboardType = KeyboardType.Phone,
            onValueChange = onPhoneNumberChange,
            onButtonClick = onSendCode,
            buttonEnabled = !state.isLoading && state.phoneNumber.isNotBlank()
        )
        KoinTextFieldWithButton(
            value = state.code,
            hint = stringResource(Res.string.sign_up_verification_hint),
            buttonText = stringResource(Res.string.common_confirm),
            keyboardType = KeyboardType.Number,
            onValueChange = onCodeChange,
            onButtonClick = onVerifyCode,
            buttonEnabled = !state.isLoading && state.isCodeSent && state.code.length == 6
        )
        state.error?.let { PasswordResetErrorText(it) }
    }
}

@Composable
private fun PasswordResetPasswordContent(
    state: PasswordResetState,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmationChange: (String) -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text(
            text = stringResource(Res.string.password_reset_password_description),
            style = KoinTheme.typography.regular15,
            color = KoinTheme.colors.neutral600
        )
        Spacer(modifier = Modifier.height(32.dp))
        KoinPasswordTextField(
            value = state.password,
            onValueChange = onPasswordChange,
            hint = stringResource(Res.string.sign_up_password_hint),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        KoinPasswordTextField(
            value = state.passwordConfirmation,
            onValueChange = onPasswordConfirmationChange,
            hint = stringResource(Res.string.sign_up_password_confirm_hint),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )
        state.error?.let {
            Spacer(modifier = Modifier.height(8.dp))
            PasswordResetErrorText(it)
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onReset,
            enabled = !state.isLoading &&
                state.password.length in 6..18 &&
                state.password == state.passwordConfirmation,
            modifier = Modifier.fillMaxWidth(),
            colors = primaryButtonColors(),
            shape = KoinTheme.shapes.small,
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            Text(
                text = stringResource(Res.string.password_reset_submit),
                style = KoinTheme.typography.medium16
            )
        }
    }
}

@Composable
private fun PasswordResetCompleteContent(
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(Res.string.password_reset_complete),
            style = KoinTheme.typography.bold20,
            color = KoinTheme.colors.neutral800
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onComplete,
            modifier = Modifier.fillMaxWidth(),
            colors = primaryButtonColors(),
            shape = KoinTheme.shapes.small,
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            Text(text = stringResource(Res.string.password_reset_go_sign_in), style = KoinTheme.typography.medium16)
        }
    }
}

@Composable
private fun PasswordResetErrorText(error: PasswordResetError) {
    KoinTextFieldAlert(
        text = stringResource(
            when (error) {
                PasswordResetError.InvalidPhone -> Res.string.password_reset_error_phone
                PasswordResetError.Send -> Res.string.password_reset_error_send
                PasswordResetError.InvalidCode -> Res.string.password_reset_error_code
                PasswordResetError.PasswordMismatch -> Res.string.password_reset_error_password
                PasswordResetError.Reset -> Res.string.password_reset_error_reset
            }
        ),
        state = TextFieldAlertState.Error
    )
}
