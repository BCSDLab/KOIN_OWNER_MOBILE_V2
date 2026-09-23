package `in`.koreatech.business.feature.event

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import dev.zacsweers.metrox.viewmodel.metroViewModel
import `in`.koreatech.business.core.designsystem.component.KoinEmptyContent
import `in`.koreatech.business.core.designsystem.component.KoinImageThumbnail
import `in`.koreatech.business.core.designsystem.component.KoinLoadingContent
import `in`.koreatech.business.core.designsystem.component.KoinScreenTitle
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_add_symbol
import `in`.koreatech.business.core.designsystem.generated.resources.common_cancel
import `in`.koreatech.business.core.designsystem.generated.resources.common_delete
import `in`.koreatech.business.core.designsystem.generated.resources.common_edit
import `in`.koreatech.business.core.designsystem.generated.resources.error_event_delete
import `in`.koreatech.business.core.designsystem.generated.resources.error_event_load
import `in`.koreatech.business.core.designsystem.generated.resources.error_event_reload
import `in`.koreatech.business.core.designsystem.generated.resources.error_shop_load
import `in`.koreatech.business.core.designsystem.generated.resources.event_delete_description
import `in`.koreatech.business.core.designsystem.generated.resources.event_delete_title
import `in`.koreatech.business.core.designsystem.generated.resources.event_deleting
import `in`.koreatech.business.core.designsystem.generated.resources.event_empty
import `in`.koreatech.business.core.designsystem.generated.resources.event_image_description
import `in`.koreatech.business.core.designsystem.generated.resources.event_period
import `in`.koreatech.business.core.designsystem.generated.resources.event_shop_required
import `in`.koreatech.business.core.designsystem.generated.resources.event_title
import `in`.koreatech.business.core.designsystem.noRippleClickable
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.domain.model.store.OwnerEvent
import org.jetbrains.compose.resources.stringResource
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun EventScreen(
    onCreateEvent: (Int) -> Unit,
    onEditEvent: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EventViewModel = metroViewModel()
) {
    val state by viewModel.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val errorMessages = mapOf(
        EventSideEffect.ShopLoadFailed to stringResource(Res.string.error_shop_load),
        EventSideEffect.EventLoadFailed to stringResource(Res.string.error_event_load),
        EventSideEffect.EventReloadFailed to stringResource(Res.string.error_event_reload),
        EventSideEffect.EventDeleteFailed to stringResource(Res.string.error_event_delete)
    )
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.onResume()
    }
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is EventSideEffect.NavigateToCreate -> onCreateEvent(sideEffect.shopId)
            is EventSideEffect.NavigateToEdit ->
                onEditEvent(
                    sideEffect.shopId,
                    sideEffect.eventId
                )
            else -> snackbarHostState.showSnackbar(errorMessages.getValue(sideEffect))
        }
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = KoinTheme.colors.neutral75,
        topBar = {
            Column {
                KoinScreenTitle(
                    text = stringResource(Res.string.event_title),
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)
                )
                state.shop?.let { shop ->
                    Text(
                        text = shop.name,
                        modifier = Modifier.padding(horizontal = 24.dp),
                        style = KoinTheme.typography.medium14,
                        color = KoinTheme.colors.neutral600
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::addEvent,
                shape = CircleShape,
                containerColor = KoinTheme.colors.primary500,
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    focusedElevation = 0.dp,
                    hoveredElevation = 0.dp
                )
            ) {
                Text(
                    text = stringResource(Res.string.common_add_symbol),
                    style = KoinTheme.typography.bold20,
                    color = Color.White
                )
            }
        }
    ) { paddingValues ->
        EventScreenImpl(
            state = state,
            onEditEvent = viewModel::editEvent,
            onDeleteEvent = viewModel::requestDelete,
            onConfirmDelete = viewModel::deleteEvent,
            onDismissDelete = viewModel::dismissDelete,
            onRefresh = viewModel::refresh,
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun EventScreenImpl(
    state: EventState,
    onEditEvent: (Int) -> Unit,
    onDeleteEvent: (Int, String) -> Unit,
    onConfirmDelete: () -> Unit,
    onDismissDelete: () -> Unit,
    onRefresh: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier.padding(contentPadding)
    ) {
        when {
            state.isLoading -> KoinLoadingContent(Modifier.fillMaxSize())
            state.shop == null ->
                KoinEmptyContent(
                    message = stringResource(Res.string.event_shop_required),
                    modifier = Modifier.fillMaxSize()
                )
            state.events.isEmpty() ->
                KoinEmptyContent(
                    message = stringResource(Res.string.event_empty),
                    modifier = Modifier.fillMaxSize()
                )
            else ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    contentPadding = PaddingValues(bottom = 96.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.events, key = { it.id }) { event ->
                        EventCard(
                            event = event,
                            onEdit = { onEditEvent(event.id) },
                            onDelete = { onDeleteEvent(event.id, event.title) }
                        )
                    }
                }
        }
    }
    if (state.deleteEventId != null) {
        AlertDialog(
            onDismissRequest = onDismissDelete,
            title = {
                Text(
                    text = stringResource(Res.string.event_delete_title),
                    style = KoinTheme.typography.bold18
                )
            },
            text = {
                Text(
                    text = stringResource(
                        Res.string.event_delete_description,
                        state.deleteEventTitle.orEmpty()
                    ),
                    style = KoinTheme.typography.regular14
                )
            },
            confirmButton = {
                TextButton(
                    enabled = !state.isDeleting,
                    onClick = onConfirmDelete
                ) {
                    Text(
                        text = stringResource(
                            if (state.isDeleting) {
                                Res.string.event_deleting
                            } else {
                                Res.string.common_delete
                            }
                        ),
                        color = KoinTheme.colors.danger600
                    )
                }
            },
            dismissButton = {
                TextButton(
                    enabled = !state.isDeleting,
                    onClick = onDismissDelete
                ) {
                    Text(text = stringResource(Res.string.common_cancel))
                }
            }
        )
    }
}

@Composable
private fun EventCard(
    event: OwnerEvent,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(
                width = 0.5.dp,
                color = KoinTheme.colors.neutral300,
                shape = RoundedCornerShape(12.dp)
            ).padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        KoinImageThumbnail(
            imageUrl = event.imageUrls.firstOrNull(),
            contentDescription = stringResource(Res.string.event_image_description, 1),
            modifier = Modifier.size(88.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = event.title,
                style = KoinTheme.typography.medium16,
                color = KoinTheme.colors.neutral800
            )
            Text(
                text = event.content,
                style = KoinTheme.typography.regular13,
                color = KoinTheme.colors.neutral600
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(
                    Res.string.event_period,
                    event.startDate,
                    event.endDate
                ),
                style = KoinTheme.typography.regular12,
                color = KoinTheme.colors.primary500
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.align(Alignment.End),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = stringResource(Res.string.common_edit),
                    style = KoinTheme.typography.medium13,
                    color = KoinTheme.colors.primary500,
                    modifier = Modifier.noRippleClickable(onClick = onEdit)
                )
                Text(
                    text = stringResource(Res.string.common_delete),
                    style = KoinTheme.typography.medium13,
                    color = KoinTheme.colors.danger600,
                    modifier = Modifier.noRippleClickable(onClick = onDelete)
                )
            }
        }
    }
}

@Preview
@Composable
private fun EventScreenPreview() {
    KoinTheme {
        EventScreenImpl(
            state = EventState(),
            onEditEvent = {},
            onDeleteEvent = { _, _ -> },
            onConfirmDelete = {},
            onDismissDelete = {},
            onRefresh = {},
            contentPadding = PaddingValues()
        )
    }
}
