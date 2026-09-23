package `in`.koreatech.business.feature.settings.license

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.ui.compose.LibraryDefaults
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import com.mikepenz.aboutlibraries.ui.compose.produceLibraries
import com.mikepenz.aboutlibraries.ui.compose.style.defaultVariantColors
import com.mikepenz.aboutlibraries.ui.compose.variant.LibraryActionMode
import `in`.koreatech.business.core.designsystem.component.topbar.KoinTopAppBar
import `in`.koreatech.business.core.designsystem.component.topbar.KoinTopAppBarDefaults
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.settings_open_source_licenses
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun OpenSourceLicensesScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val libraries by produceLibraries {
        Res.readBytes("files/aboutlibraries.json").decodeToString()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            KoinTopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.settings_open_source_licenses),
                        style = KoinTheme.typography.medium18
                    )
                },
                colors = KoinTopAppBarDefaults.topAppBarColors(
                    containerColor = KoinTheme.colors.neutral75
                ),
                onNavigationIconClick = onBack
            )
        },
        containerColor = KoinTheme.colors.neutral75
    ) { paddingValues ->
        OpenSourceLicensesScreenImpl(
            libraries = libraries,
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        )
    }
}

@Composable
fun OpenSourceLicensesScreenImpl(
    libraries: Libs?,
    modifier: Modifier = Modifier
) {
    LibrariesContainer(
        libraries = libraries,
        actionMode = LibraryActionMode.Icons,
        variantColors = LibraryDefaults.defaultVariantColors(
            rowBackground = KoinTheme.colors.neutral75,
            rowExpandedBackground = KoinTheme.colors.neutral75,
            actionFilledContainer = KoinTheme.colors.primary300,
            actionFilledContent = KoinTheme.colors.primary700,
            actionOutlineBorder = KoinTheme.colors.primary300,
            actionOutlineContent = KoinTheme.colors.primary500
        ),
        modifier = modifier
    )
}
