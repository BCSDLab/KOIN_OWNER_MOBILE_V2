package `in`.koreatech.business.feature.settings.terms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.zacsweers.metrox.viewmodel.metroViewModel
import `in`.koreatech.business.core.designsystem.component.KoinLoadingContent
import `in`.koreatech.business.core.designsystem.component.KoinSelectableChipGroup
import `in`.koreatech.business.core.designsystem.component.KoinSelectableItem
import `in`.koreatech.business.core.designsystem.component.topbar.KoinTopAppBar
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.settings_terms
import `in`.koreatech.business.core.designsystem.generated.resources.terms_marketing
import `in`.koreatech.business.core.designsystem.generated.resources.terms_privacy
import `in`.koreatech.business.core.designsystem.generated.resources.terms_service
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import org.jetbrains.compose.resources.stringResource
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun TermsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TermsViewModel = metroViewModel()
) {
    val state by viewModel.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            KoinTopAppBar(
                title = { Text(stringResource(Res.string.settings_terms), style = KoinTheme.typography.medium18) },
                onNavigationIconClick = onBack
            )
        },
        containerColor = KoinTheme.colors.neutral0
    ) { paddingValues ->
        TermsScreenImpl(
            state = state,
            onTermClick = viewModel::selectTerm,
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        )
    }
}

@Composable
fun TermsScreenImpl(
    state: TermsState,
    onTermClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        KoinLoadingContent(modifier)
        return
    }

    Column(modifier = modifier) {
        KoinSelectableChipGroup(
            items =
            state.terms.mapIndexed { index, term ->
                KoinSelectableItem(index, term.type.title())
            },
            selectedItemId = state.selectedIndex,
            onItemClick = onTermClick,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
        )
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            state.terms.getOrNull(state.selectedIndex)?.let { term ->
                Text(
                    text = term.type.title(),
                    style = KoinTheme.typography.bold20,
                    color = KoinTheme.colors.neutral800
                )
                Text(term.content, style = KoinTheme.typography.regular14, color = KoinTheme.colors.neutral700)
            }
        }
    }
}

@Composable
private fun TermType.title(): String =
    stringResource(
        when (this) {
            TermType.Service -> Res.string.terms_service
            TermType.Privacy -> Res.string.terms_privacy
            TermType.Marketing -> Res.string.terms_marketing
        }
    )
