package `in`.koreatech.business.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import dev.zacsweers.metrox.viewmodel.metroViewModel
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_forward_symbol
import `in`.koreatech.business.core.designsystem.generated.resources.common_koin
import `in`.koreatech.business.core.designsystem.generated.resources.error_shop_load_retry
import `in`.koreatech.business.core.designsystem.generated.resources.home_event_badge
import `in`.koreatech.business.core.designsystem.generated.resources.home_event_management
import `in`.koreatech.business.core.designsystem.generated.resources.home_event_management_description
import `in`.koreatech.business.core.designsystem.generated.resources.home_greeting
import `in`.koreatech.business.core.designsystem.generated.resources.home_menu_badge
import `in`.koreatech.business.core.designsystem.generated.resources.home_menu_management
import `in`.koreatech.business.core.designsystem.generated.resources.home_menu_management_description
import `in`.koreatech.business.core.designsystem.generated.resources.home_my_shop
import `in`.koreatech.business.core.designsystem.generated.resources.home_notification
import `in`.koreatech.business.core.designsystem.generated.resources.home_register_shop
import `in`.koreatech.business.core.designsystem.generated.resources.home_register_shop_description
import `in`.koreatech.business.core.designsystem.generated.resources.home_shortcut
import `in`.koreatech.business.core.designsystem.generated.resources.ic_bcsd_symbol
import `in`.koreatech.business.core.designsystem.generated.resources.ic_koin_text
import `in`.koreatech.business.core.designsystem.generated.resources.ic_rebrand_notification
import `in`.koreatech.business.core.designsystem.noRippleClickable
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.feature.home.component.ManagedShopCard
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

private val HomeBackground = Color(0xFFF8F8FA)
private val HomeBorder = Color(0xFFE6E6E6)
private val HomeDescription = Color(0xFFA8A8A8)

@Composable
fun HomeScreen(
    onShopClick: (Int) -> Unit = {},
    onRegisterShopClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    onEventClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = metroViewModel()
) {
    val state by viewModel.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val errorMessage = stringResource(Res.string.error_shop_load_retry)
    viewModel.collectSideEffect { snackbarHostState.showSnackbar(errorMessage) }
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.onResume()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = HomeBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        HomeScreenImpl(
            state = state,
            onShopClick = onShopClick,
            onRegisterShopClick = onRegisterShopClick,
            onNotificationClick = onNotificationClick,
            onMenuClick = onMenuClick,
            onEventClick = onEventClick,
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        )
    }
}

@Composable
fun HomeScreenImpl(
    state: HomeState,
    onShopClick: (Int) -> Unit,
    onRegisterShopClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onMenuClick: () -> Unit,
    onEventClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.background(HomeBackground).verticalScroll(rememberScrollState())) {
        HomeTopBar(onNotificationClick = onNotificationClick)
        HomeGreeting()
        HomeSection(
            title = stringResource(Res.string.home_my_shop),
            more = null
        ) {
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxWidth().height(160.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = KoinTheme.colors.primary500)
                }
            } else if (state.shop == null) {
                EmptyShopCard(onRegisterShopClick = onRegisterShopClick)
            } else {
                ManagedShopCard(shop = state.shop, onClick = { onShopClick(state.shop.id) })
            }
        }
        HomeSection(title = stringResource(Res.string.home_shortcut)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                HomeFeatureCard(
                    stringResource(Res.string.home_menu_management),
                    stringResource(Res.string.home_menu_management_description),
                    stringResource(Res.string.home_menu_badge),
                    onMenuClick,
                    Modifier.weight(1f)
                )
                HomeFeatureCard(
                    stringResource(Res.string.home_event_management),
                    stringResource(Res.string.home_event_management_description),
                    stringResource(Res.string.home_event_badge),
                    onEventClick,
                    Modifier.weight(1f)
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun HomeTopBar(
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_bcsd_symbol),
            contentDescription = null,
            modifier = Modifier.width(47.dp).height(37.dp)
        )
        Image(
            painter = painterResource(Res.drawable.ic_koin_text),
            contentDescription = stringResource(Res.string.common_koin),
            modifier = Modifier.width(63.dp).height(21.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(Res.drawable.ic_rebrand_notification),
            contentDescription = stringResource(Res.string.home_notification),
            modifier = Modifier.size(24.dp).noRippleClickable(onClick = onNotificationClick)
        )
    }
}

@Composable
private fun HomeGreeting(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(top = 16.dp, start = 24.dp, end = 24.dp)) {
        Text(
            stringResource(Res.string.home_greeting),
            style = KoinTheme.typography.bold20.copy(fontSize = 24.sp, lineHeight = 32.sp),
            color = KoinTheme.colors.neutral800
        )
    }
}

@Composable
private fun HomeSection(
    title: String,
    modifier: Modifier = Modifier,
    more: String? = null,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(top = 16.dp, start = 24.dp, end = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                title,
                style = KoinTheme.typography.medium18,
                color = KoinTheme.colors.neutral800,
                modifier = Modifier.weight(1f)
            )
            more?.let {
                Text(it, style = KoinTheme.typography.regular13, color = KoinTheme.colors.neutral500)
            }
        }
        content()
    }
}

@Composable
private fun EmptyShopCard(
    onRegisterShopClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier =
        modifier
            .fillMaxWidth()
            .homeCard()
            .noRippleClickable(onClick = onRegisterShopClick)
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HomeBadge("+")
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(stringResource(Res.string.home_register_shop), style = KoinTheme.typography.medium16, color = KoinTheme.colors.neutral800)
            Text(
                stringResource(Res.string.home_register_shop_description),
                style = KoinTheme.typography.regular13,
                color = HomeDescription
            )
        }
        HomeArrow()
    }
}

@Composable
private fun HomeFeatureCard(
    title: String,
    description: String,
    badge: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.homeCard().noRippleClickable(onClick = onClick).padding(20.dp)) {
        HomeBadge(badge)
        Spacer(modifier = Modifier.height(6.dp))
        Text(title, style = KoinTheme.typography.medium15, color = KoinTheme.colors.neutral800)
        Text(description, style = KoinTheme.typography.regular12, color = HomeDescription)
        Spacer(modifier = Modifier.height(6.dp))
        Text(stringResource(Res.string.home_shortcut), style = KoinTheme.typography.regular10, color = KoinTheme.colors.primary500)
    }
}

private fun Modifier.homeCard(): Modifier =
    border(0.5.dp, HomeBorder, RoundedCornerShape(16.dp))
        .clip(RoundedCornerShape(16.dp))
        .background(Color.White)

@Composable
private fun HomeBadge(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier =
        modifier
            .size(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(KoinTheme.colors.primary100),
        contentAlignment = Alignment.Center
    ) {
        Text(text, style = KoinTheme.typography.bold16, color = KoinTheme.colors.primary600)
    }
}

@Composable
private fun HomeArrow() {
    Text(stringResource(Res.string.common_forward_symbol), style = KoinTheme.typography.medium18, color = KoinTheme.colors.neutral500)
}

@Preview
@Composable
private fun HomeScreenImplPreview() {
    KoinTheme {
        HomeScreenImpl(state = HomeState(), {}, {}, {}, {}, {})
    }
}
