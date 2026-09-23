package `in`.koreatech.business.feature.signup.term

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.component.button.primaryButtonColors
import `in`.koreatech.business.core.designsystem.component.progress.KoinProgressHeader
import `in`.koreatech.business.core.designsystem.component.progress.KoinProgressIndicator
import `in`.koreatech.business.core.designsystem.component.selection.KoinCheckBox
import `in`.koreatech.business.core.designsystem.component.topbar.KoinTopAppBar
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_next
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_term_all
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_term_marketing
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_term_privacy
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_term_service
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_term_step
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_title
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.feature.signup.SignupState
import `in`.koreatech.business.feature.signup.component.SignupCheckAllBox
import `in`.koreatech.business.feature.signup.component.SignupTerm
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SignUpTermScreen(
    state: SignupState,
    onBack: () -> Unit,
    onPrivacyTermCheckChange: (Boolean) -> Unit,
    onKoinTermCheckChange: (Boolean) -> Unit,
    onMarketingTermCheckChange: (Boolean) -> Unit,
    onAllTermCheckChange: (Boolean) -> Unit,
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
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
        ) {
            KoinProgressHeader(text = stringResource(Res.string.sign_up_term_step), currentStep = 1, maxStep = 6)
            Spacer(modifier = Modifier.height(8.dp))
            KoinProgressIndicator(currentStep = 1, maxStep = 6)
            Spacer(modifier = Modifier.height(16.dp))
            SignupCheckAllBox(
                checked = state.termsAgreed,
                text = stringResource(Res.string.sign_up_term_all),
                onCheckedChange = onAllTermCheckChange
            )
            Spacer(modifier = Modifier.height(16.dp))
            KoinCheckBox(state.agreedToPrivacy, stringResource(Res.string.sign_up_term_privacy), onPrivacyTermCheckChange)
            Spacer(modifier = Modifier.height(16.dp))
            SignupTerm(state.privacyTerm)
            Spacer(modifier = Modifier.height(16.dp))
            KoinCheckBox(state.agreedToService, stringResource(Res.string.sign_up_term_service), onKoinTermCheckChange)
            Spacer(modifier = Modifier.height(16.dp))
            SignupTerm(state.koinTerm)
            Spacer(modifier = Modifier.height(16.dp))
            KoinCheckBox(state.agreedToMarketing, stringResource(Res.string.sign_up_term_marketing), onMarketingTermCheckChange)
            Spacer(modifier = Modifier.height(16.dp))
            SignupTerm(state.marketingTerm)
            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = navigateToNextScreen,
                enabled = state.agreedToPrivacy && state.agreedToService,
                shape = KoinTheme.shapes.small,
                colors = primaryButtonColors(),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) { Text(stringResource(Res.string.common_next), style = KoinTheme.typography.medium16) }
        }
    }
}
