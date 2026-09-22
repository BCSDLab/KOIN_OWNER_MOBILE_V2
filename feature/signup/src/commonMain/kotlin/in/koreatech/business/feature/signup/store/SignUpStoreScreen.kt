package `in`.koreatech.business.feature.signup.store

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.component.topbar.KoinTopAppBar
import `in`.koreatech.business.core.designsystem.component.user.KoinUserBasicTextField
import `in`.koreatech.business.core.designsystem.component.user.KoinUserProgressHeader
import `in`.koreatech.business.core.designsystem.component.user.KoinUserProgressIndicator
import `in`.koreatech.business.core.designsystem.component.user.signupButtonColors
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_next
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_store_input
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_store_name_hint
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_store_phone_hint
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_store_search
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_store_step
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_title
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.feature.signup.SignupState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SignUpStoreScreen(
    state: SignupState,
    onBack: () -> Unit,
    onStoreNameChange: (String) -> Unit,
    onStorePhoneNumberChange: (String) -> Unit,
    onSearchStore: () -> Unit,
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
        containerColor = KoinTheme.colors.neutral0
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
            KoinUserProgressHeader(text = stringResource(Res.string.sign_up_store_step), currentStep = 5, maxStep = 6)
            Spacer(modifier = Modifier.height(8.dp))
            KoinUserProgressIndicator(currentStep = 5, maxStep = 6)
            Spacer(modifier = Modifier.height(64.dp))
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(text = stringResource(Res.string.sign_up_store_input), style = KoinTheme.typography.medium16)
                Spacer(modifier = Modifier.height(16.dp))
                KoinUserBasicTextField(state.storeName, onStoreNameChange, hint = stringResource(Res.string.sign_up_store_name_hint))
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onSearchStore,
                    shape = KoinTheme.shapes.small,
                    colors = signupButtonColors(),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.sign_up_store_search),
                        style = KoinTheme.typography.medium15
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                KoinUserBasicTextField(
                    value = state.storePhoneNumber,
                    onValueChange = onStorePhoneNumberChange,
                    hint = stringResource(Res.string.sign_up_store_phone_hint),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = navigateToNextScreen,
                enabled = state.storeName.isNotBlank(),
                shape = KoinTheme.shapes.small,
                colors = signupButtonColors(),
                contentPadding = PaddingValues(12.dp)
            ) { Text(stringResource(Res.string.common_next), style = KoinTheme.typography.medium15) }
        }
    }
}
