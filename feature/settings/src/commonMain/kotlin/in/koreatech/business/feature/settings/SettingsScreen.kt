package `in`.koreatech.business.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.zacsweers.metrox.viewmodel.metroViewModel
import `in`.koreatech.business.core.designsystem.component.KoinScreenTitle
import `in`.koreatech.business.core.designsystem.component.KoinSettingItem
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_cancel
import `in`.koreatech.business.core.designsystem.generated.resources.common_delete
import `in`.koreatech.business.core.designsystem.generated.resources.settings_delete_owner
import `in`.koreatech.business.core.designsystem.generated.resources.settings_delete_owner_description
import `in`.koreatech.business.core.designsystem.generated.resources.settings_delete_owner_error
import `in`.koreatech.business.core.designsystem.generated.resources.settings_delete_owner_title
import `in`.koreatech.business.core.designsystem.generated.resources.settings_manage_shops
import `in`.koreatech.business.core.designsystem.generated.resources.settings_open_source_licenses
import `in`.koreatech.business.core.designsystem.generated.resources.settings_sign_out
import `in`.koreatech.business.core.designsystem.generated.resources.settings_terms
import `in`.koreatech.business.core.designsystem.generated.resources.settings_title
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import org.jetbrains.compose.resources.stringResource
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SettingsScreen(
    onManageShopsClick: () -> Unit,
    onTermsClick: () -> Unit,
    onOpenSourceLicensesClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = metroViewModel()
) {
    val state by viewModel.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val deleteOwnerError = stringResource(Res.string.settings_delete_owner_error)
    viewModel.collectSideEffect { snackbarHostState.showSnackbar(deleteOwnerError) }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = KoinTheme.colors.neutral75,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            KoinScreenTitle(
                text = stringResource(Res.string.settings_title),
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)
            )
        }
    ) { paddingValues ->
        SettingsScreenImpl(
            onManageShopsClick = onManageShopsClick,
            onTermsClick = onTermsClick,
            onOpenSourceLicensesClick = onOpenSourceLicensesClick,
            onSignOut = viewModel::signOut,
            onDeleteOwner = viewModel::showDeleteOwnerDialog,
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        )
    }
    if (state.isDeleteOwnerDialogVisible) {
        AlertDialog(
            onDismissRequest = viewModel::dismissDeleteOwnerDialog,
            title = {
                Text(
                    text = stringResource(Res.string.settings_delete_owner_title),
                    style = KoinTheme.typography.bold18
                )
            },
            text = {
                Text(
                    text = stringResource(Res.string.settings_delete_owner_description),
                    style = KoinTheme.typography.regular14
                )
            },
            confirmButton = {
                TextButton(
                    enabled = !state.isDeletingOwner,
                    onClick = viewModel::deleteOwner
                ) {
                    Text(
                        text = stringResource(Res.string.common_delete),
                        color = KoinTheme.colors.danger600
                    )
                }
            },
            dismissButton = {
                TextButton(
                    enabled = !state.isDeletingOwner,
                    onClick = viewModel::dismissDeleteOwnerDialog
                ) {
                    Text(stringResource(Res.string.common_cancel))
                }
            }
        )
    }
}

@Composable
fun SettingsScreenImpl(
    onManageShopsClick: () -> Unit,
    onTermsClick: () -> Unit,
    onOpenSourceLicensesClick: () -> Unit,
    onSignOut: () -> Unit,
    onDeleteOwner: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        KoinSettingItem(stringResource(Res.string.settings_manage_shops), onManageShopsClick)
        HorizontalDivider(color = KoinTheme.colors.neutral200)
        KoinSettingItem(stringResource(Res.string.settings_terms), onTermsClick)
        HorizontalDivider(color = KoinTheme.colors.neutral200)
        KoinSettingItem(stringResource(Res.string.settings_open_source_licenses), onOpenSourceLicensesClick)
        HorizontalDivider(color = KoinTheme.colors.neutral200)
        KoinSettingItem(stringResource(Res.string.settings_sign_out), onSignOut)
        HorizontalDivider(color = KoinTheme.colors.neutral200)
        KoinSettingItem(stringResource(Res.string.settings_delete_owner), onDeleteOwner)
        HorizontalDivider(color = KoinTheme.colors.neutral200)
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    KoinTheme { SettingsScreenImpl({}, {}, {}, {}, {}) }
}
