package `in`.koreatech.business.feature.signup.attachment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.component.button.primaryButtonColors
import `in`.koreatech.business.core.designsystem.component.topbar.KoinTopAppBar
import `in`.koreatech.business.core.designsystem.component.user.AlertState
import `in`.koreatech.business.core.designsystem.component.user.KoinUserProgressHeader
import `in`.koreatech.business.core.designsystem.component.user.KoinUserProgressIndicator
import `in`.koreatech.business.core.designsystem.component.user.KoinUserTextFieldAlert
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_delete
import `in`.koreatech.business.core.designsystem.generated.resources.error_business_number_check
import `in`.koreatech.business.core.designsystem.generated.resources.error_file_upload
import `in`.koreatech.business.core.designsystem.generated.resources.error_phone_verification_required
import `in`.koreatech.business.core.designsystem.generated.resources.error_sign_up_submit
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_attachment_add
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_attachment_count
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_attachment_guide
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_attachment_input
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_attachment_step
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_attachment_uploading
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_store_search_error
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_submit
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_title
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.core.file.rememberImageFilePicker
import `in`.koreatech.business.feature.signup.SignupError
import `in`.koreatech.business.feature.signup.SignupState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SignUpAttachmentScreen(
    state: SignupState,
    onBack: () -> Unit,
    onUploadFile: (String, String, ByteArray) -> Unit,
    onFileSelectionFailed: () -> Unit,
    onDeleteFile: (Int) -> Unit,
    navigateToNextScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val openImagePicker = rememberImageFilePicker(
        onImagePicked = { onUploadFile(it.name, it.contentType, it.bytes) },
        onFailure = { onFileSelectionFailed() }
    )

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
            KoinUserProgressHeader(stringResource(Res.string.sign_up_attachment_step), 6, 6)
            Spacer(modifier = Modifier.height(8.dp))
            KoinUserProgressIndicator(6, 6)
            Spacer(modifier = Modifier.height(64.dp))
            Text(stringResource(Res.string.sign_up_attachment_input), style = KoinTheme.typography.medium16)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    stringResource(Res.string.sign_up_attachment_guide),
                    style = KoinTheme.typography.regular12,
                    color = KoinTheme.colors.neutral500
                )
                Text(
                    stringResource(Res.string.sign_up_attachment_count, state.selectedImages.size),
                    style = KoinTheme.typography.regular12,
                    color = KoinTheme.colors.primary500
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            state.selectedImages.forEachIndexed { index, file ->
                Row(
                    modifier = Modifier.fillMaxWidth().background(KoinTheme.colors.neutral100).padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(file.title, style = KoinTheme.typography.regular14, color = KoinTheme.colors.neutral600)
                    Button(onClick = { onDeleteFile(index) }, colors = primaryButtonColors()) {
                        Text(stringResource(Res.string.common_delete), style = KoinTheme.typography.regular12)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = state.selectedImages.size < 5 && !state.isUploading,
                onClick = openImagePicker,
                shape = KoinTheme.shapes.small,
                colors = primaryButtonColors(),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text(
                    stringResource(if (state.isUploading) Res.string.sign_up_attachment_uploading else Res.string.sign_up_attachment_add),
                    style = KoinTheme.typography.medium15
                )
            }
            state.error?.let { error ->
                Spacer(modifier = Modifier.height(8.dp))
                KoinUserTextFieldAlert(
                    text = when (error) {
                        SignupError.PhoneVerificationRequired ->
                            stringResource(
                                Res.string.error_phone_verification_required
                            )
                        SignupError.FileUpload -> stringResource(Res.string.error_file_upload)
                        SignupError.BusinessNumberCheck ->
                            stringResource(
                                Res.string.error_business_number_check
                            )
                        SignupError.StoreSearch -> stringResource(Res.string.sign_up_store_search_error)
                        SignupError.Submit -> stringResource(Res.string.error_sign_up_submit)
                        is SignupError.Dynamic -> error.message
                    },
                    state = AlertState.Error
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = state.fileInfo.isNotEmpty() && !state.isUploading && !state.isLoading,
                onClick = navigateToNextScreen,
                shape = KoinTheme.shapes.small,
                colors = primaryButtonColors(),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text(stringResource(Res.string.sign_up_submit), style = KoinTheme.typography.medium16)
            }
        }
    }
}
