package `in`.koreatech.business.core.designsystem.component.topbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_back
import `in`.koreatech.business.core.designsystem.generated.resources.ic_toolbar_navigate_back
import `in`.koreatech.business.core.designsystem.noRippleClickable
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KoinTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit = {},
    actions: @Composable (RowScope.() -> Unit) = {},
    colors: TopAppBarColors = KoinTopAppBarDefaults.topAppBarColors(),
    windowInsets: WindowInsets = KoinTopAppBarDefaults.windowInsets,
    expandedHeight: Dp = KoinTopAppBarDefaults.KoinTopAppBarExpandedHeight,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    CenterAlignedTopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = {
            Icon(
                modifier = Modifier.padding(start = 20.dp).size(24.dp).noRippleClickable(onClick = onNavigationIconClick),
                imageVector = vectorResource(Res.drawable.ic_toolbar_navigate_back),
                contentDescription = stringResource(Res.string.common_back)
            )
        },
        actions = {
            Row(
                modifier = Modifier.padding(end = 20.dp),
                content = actions
            )
        },
        colors = colors,
        windowInsets = windowInsets,
        expandedHeight = expandedHeight,
        scrollBehavior = scrollBehavior
    )
}

object KoinTopAppBarDefaults {
    @Composable
    fun topAppBarColors(
        containerColor: Color = KoinTheme.colors.neutral75,
        scrolledContainerColor: Color = Color.Unspecified,
        navigationIconContentColor: Color = KoinTheme.colors.neutral800,
        titleContentColor: Color = KoinTheme.colors.neutral800,
        actionIconContentColor: Color = KoinTheme.colors.neutral800,
        subtitleContentColor: Color = Color.Unspecified
    ): TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor,
        scrolledContainerColor,
        navigationIconContentColor,
        titleContentColor,
        actionIconContentColor,
        subtitleContentColor
    )

    val KoinTopAppBarExpandedHeight: Dp = 56.dp
    val windowInsets: WindowInsets
        @Composable get() = WindowInsets.systemBars.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
}
