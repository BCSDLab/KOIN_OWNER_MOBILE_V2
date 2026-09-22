package `in`.koreatech.business.feature.signin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.zacsweers.metrox.viewmodel.metroViewModel
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_koin
import `in`.koreatech.business.core.designsystem.generated.resources.common_koin_logo
import `in`.koreatech.business.core.designsystem.generated.resources.ic_bcsd_symbol
import `in`.koreatech.business.core.designsystem.generated.resources.ic_koin_text
import `in`.koreatech.business.core.designsystem.generated.resources.sign_in_error
import `in`.koreatech.business.core.designsystem.generated.resources.sign_in_find_password
import `in`.koreatech.business.core.designsystem.generated.resources.sign_in_id_hint
import `in`.koreatech.business.core.designsystem.generated.resources.sign_in_loading
import `in`.koreatech.business.core.designsystem.generated.resources.sign_in_password_hint
import `in`.koreatech.business.core.designsystem.generated.resources.sign_in_sign_up
import `in`.koreatech.business.core.designsystem.generated.resources.sign_in_submit
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.feature.signin.component.SignInTextField
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SignInScreen(
    onSignUpClick: () -> Unit,
    onFindPasswordClick: () -> Unit = {},
    onSignInSuccess: () -> Unit = {},
    viewModel: SignInViewModel = metroViewModel()
) {
    val state by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            SignInSideEffect.SignInSuccess -> onSignInSuccess()
        }
    }

    SignInScreenImpl(
        loginId = state.phoneNumber,
        password = state.password,
        isError = state.error != null,
        isLoading = state.isLoading,
        setLoginId = viewModel::updatePhoneNumber,
        setPassword = viewModel::updatePassword,
        signIn = viewModel::signIn,
        onSignUpClick = onSignUpClick,
        onFindPasswordClick = onFindPasswordClick
    )
}

@Composable
fun SignInScreenImpl(
    loginId: String,
    password: String,
    isError: Boolean,
    isLoading: Boolean,
    setLoginId: (String) -> Unit,
    setPassword: (String) -> Unit,
    signIn: () -> Unit,
    onSignUpClick: () -> Unit,
    onFindPasswordClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Scaffold(modifier = modifier, containerColor = KoinTheme.colors.neutral0) { paddingValues ->
        Column(
            modifier =
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 40.dp)
                .verticalScroll(scrollState)
                .imePadding(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(82.dp))

            Column {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painterResource(Res.drawable.ic_bcsd_symbol),
                        contentDescription = stringResource(Res.string.common_koin_logo),
                        modifier = Modifier.width(80.dp).height(60.dp)
                    )
                    Spacer(Modifier.height(4.dp))
                    Image(
                        painterResource(Res.drawable.ic_koin_text),
                        contentDescription = stringResource(Res.string.common_koin),
                        modifier = Modifier.width(100.dp).height(30.dp)
                    )
                }

                Spacer(Modifier.height(50.dp))

                SignInTextField(
                    value = loginId,
                    onValueChange = setLoginId,
                    hint = stringResource(Res.string.sign_in_id_hint),
                    keyboardOptions =
                    KeyboardOptions(
                        keyboardType = if (loginId.all(Char::isDigit)) KeyboardType.Phone else KeyboardType.Text
                    ),
                    modifier = Modifier.fillMaxWidth().padding(top = 32.dp)
                )

                Spacer(Modifier.height(24.dp))

                SignInTextField(
                    value = password,
                    onValueChange = setPassword,
                    hint = stringResource(Res.string.sign_in_password_hint),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    isPassword = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Box(Modifier.height(48.dp)) {
                    if (isError) {
                        Text(
                            stringResource(Res.string.sign_in_error),
                            color = KoinTheme.colors.primary500,
                            style = KoinTheme.typography.regular12,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(containerColor = KoinTheme.colors.primary500),
                    onClick = signIn
                ) {
                    Text(
                        stringResource(if (isLoading) Res.string.sign_in_loading else Res.string.sign_in_submit),
                        style = KoinTheme.typography.medium15
                    )
                }

                Spacer(Modifier.height(24.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = KoinTheme.colors.neutral0),
                    border = BorderStroke(1.dp, KoinTheme.colors.primary500),
                    onClick = onSignUpClick
                ) {
                    Text(
                        stringResource(Res.string.sign_in_sign_up),
                        color = KoinTheme.colors.primary500,
                        style = KoinTheme.typography.medium15
                    )
                }

                Spacer(Modifier.height(32.dp))

                Text(
                    stringResource(Res.string.sign_in_find_password),
                    color = KoinTheme.colors.neutral600,
                    style = KoinTheme.typography.regular12,
                    modifier = Modifier.fillMaxWidth().clickable(onClick = onFindPasswordClick),
                    textAlign = TextAlign.Center
                )
            }

            Column {
                Spacer(Modifier.height(24.dp))
                Text(
                    "Copyright © KOIN. All rights reserved.",
                    color = KoinTheme.colors.neutral600,
                    style = KoinTheme.typography.regular12,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
