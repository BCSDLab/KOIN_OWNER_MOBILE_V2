package `in`.koreatech.business.feature.signup.verification

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.component.topbar.KoinTopAppBar
import `in`.koreatech.business.core.designsystem.component.user.AlertState
import `in`.koreatech.business.core.designsystem.component.user.KoinUserBasicTextField
import `in`.koreatech.business.core.designsystem.component.user.KoinUserProgressHeader
import `in`.koreatech.business.core.designsystem.component.user.KoinUserProgressIndicator
import `in`.koreatech.business.core.designsystem.component.user.KoinUserTextFieldAlert
import `in`.koreatech.business.core.designsystem.component.user.KoinUserWithButtonItem
import `in`.koreatech.business.core.designsystem.component.user.signupButtonColors
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_confirm
import `in`.koreatech.business.core.designsystem.generated.resources.common_next
import `in`.koreatech.business.core.designsystem.generated.resources.error_verification_send
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_name_hint
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_name_input
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_phone_exists
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_phone_hint
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_phone_input
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_phone_invalid
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_title
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_verification_confirmed
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_verification_hint
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_verification_invalid
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_verification_resend
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_verification_send
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_verification_sent
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_verification_step
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.feature.signup.SignupState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SignUpVerificationScreen(
    state: SignupState,
    onBack: () -> Unit,
    onNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onVerificationCodeChange: (String) -> Unit,
    onSendVerificationCode: () -> Unit,
    onVerifyCode: () -> Unit,
    navigateToNextScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            KoinTopAppBar(
                title = {
                    Text(
                        stringResource(Res.string.sign_up_title),
                        style = KoinTheme.typography.medium18
                    )
                },
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
                .padding(bottom = 40.dp)
        ) {
            KoinUserProgressHeader(
                text = stringResource(Res.string.sign_up_verification_step),
                currentStep = 2,
                maxStep = 6
            )
            Spacer(modifier = Modifier.height(8.dp))
            KoinUserProgressIndicator(currentStep = 2, maxStep = 6)
            Spacer(modifier = Modifier.height(64.dp))
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(
                    text = stringResource(Res.string.sign_up_name_input),
                    style = KoinTheme.typography.medium16
                )
                Spacer(modifier = Modifier.height(16.dp))
                KoinUserBasicTextField(
                    value = state.name,
                    onValueChange = onNameChange,
                    hint = stringResource(Res.string.sign_up_name_hint),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
                Spacer(modifier = Modifier.height(64.dp))
                Text(
                    text = stringResource(Res.string.sign_up_phone_input),
                    style = KoinTheme.typography.medium16
                )
                Spacer(modifier = Modifier.height(8.dp))
                KoinUserWithButtonItem(
                    value = state.phoneNumber,
                    hint = stringResource(Res.string.sign_up_phone_hint),
                    buttonText =
                    if (state.isVerificationCodeSent) {
                        stringResource(Res.string.sign_up_verification_resend)
                    } else {
                        stringResource(Res.string.sign_up_verification_send)
                    },
                    keyboardType = KeyboardType.Number,
                    onValueChange = onPhoneNumberChange,
                    onButtonClick = onSendVerificationCode,
                    buttonEnabled =
                    state.phoneNumber.isNotBlank() &&
                        state.verificationCodeState !is VerificationCodeState.Valid &&
                        !state.isLoading
                )
                when (val phoneNumberState = state.phoneNumberVerificationState) {
                    PhoneNumberVerificationState.AlreadySignedUp ->
                        KoinUserTextFieldAlert(
                            stringResource(Res.string.sign_up_phone_exists),
                            AlertState.Error
                        )

                    PhoneNumberVerificationState.WrongFormat ->
                        KoinUserTextFieldAlert(
                            stringResource(Res.string.sign_up_phone_invalid),
                            AlertState.Warning
                        )

                    PhoneNumberVerificationState.Sent ->
                        KoinUserTextFieldAlert(
                            stringResource(Res.string.sign_up_verification_sent),
                            AlertState.Success
                        )

                    is PhoneNumberVerificationState.Failed ->
                        KoinUserTextFieldAlert(
                            phoneNumberState.message
                                ?: stringResource(Res.string.error_verification_send),
                            AlertState.Error
                        )

                    PhoneNumberVerificationState.None -> Unit
                }
                Spacer(modifier = Modifier.height(24.dp))
                KoinUserWithButtonItem(
                    value = state.verificationCode,
                    hint = stringResource(Res.string.sign_up_verification_hint),
                    buttonText = stringResource(Res.string.common_confirm),
                    keyboardType = KeyboardType.Number,
                    onValueChange = onVerificationCodeChange,
                    onButtonClick = onVerifyCode,
                    buttonEnabled =
                    state.isVerificationCodeSent &&
                        state.verificationCode.length == 6 &&
                        state.verificationCodeState !is VerificationCodeState.Valid &&
                        !state.isLoading
                )
                when (state.verificationCodeState) {
                    VerificationCodeState.Valid ->
                        KoinUserTextFieldAlert(
                            stringResource(Res.string.sign_up_verification_confirmed),
                            AlertState.Success
                        )

                    VerificationCodeState.NotValid ->
                        KoinUserTextFieldAlert(
                            stringResource(Res.string.sign_up_verification_invalid),
                            AlertState.Error
                        )

                    VerificationCodeState.None -> Unit
                }
            }
            Spacer(modifier = Modifier.height(64.dp))
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = navigateToNextScreen,
                enabled =
                state.name.isNotBlank() &&
                    state.verificationCodeState is VerificationCodeState.Valid &&
                    !state.isLoading,
                shape = KoinTheme.shapes.small,
                colors = signupButtonColors(),
                contentPadding = PaddingValues(12.dp)
            ) {
                Text(
                    stringResource(Res.string.common_next),
                    style = KoinTheme.typography.medium15
                )
            }
        }
    }
}
