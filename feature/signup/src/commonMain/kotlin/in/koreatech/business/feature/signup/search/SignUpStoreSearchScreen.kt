package `in`.koreatech.business.feature.signup.search

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.component.topbar.KoinTopAppBar
import `in`.koreatech.business.core.designsystem.component.user.KoinUserBasicTextField
import `in`.koreatech.business.core.designsystem.component.user.signupButtonColors
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_next
import `in`.koreatech.business.core.designsystem.generated.resources.home_bank_transfer
import `in`.koreatech.business.core.designsystem.generated.resources.home_card
import `in`.koreatech.business.core.designsystem.generated.resources.home_delivery
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_store_search
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_store_search_empty
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_store_search_error
import `in`.koreatech.business.core.designsystem.generated.resources.sign_up_store_search_hint
import `in`.koreatech.business.core.designsystem.noRippleClickable
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.domain.model.store.StoreSearchResult
import `in`.koreatech.business.feature.signup.SignupError
import `in`.koreatech.business.feature.signup.SignupState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SignUpStoreSearchScreen(
    state: SignupState,
    onBack: () -> Unit,
    onQueryChange: (String) -> Unit,
    onStoreClick: (Int) -> Unit,
    onSelect: () -> Unit,
    onLoad: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onLoad()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            KoinTopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.sign_up_store_search),
                        style = KoinTheme.typography.medium18
                    )
                },
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
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            KoinUserBasicTextField(
                value = state.storeSearchQuery,
                onValueChange = onQueryChange,
                hint = stringResource(Res.string.sign_up_store_search_hint)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentAlignment = Alignment.Center
            ) {
                when {
                    state.isSearchingStores -> CircularProgressIndicator(color = KoinTheme.colors.primary500)
                    state.error == SignupError.StoreSearch ->
                        Text(
                            text = stringResource(Res.string.sign_up_store_search_error),
                            style = KoinTheme.typography.regular14,
                            color = KoinTheme.colors.neutral500
                        )
                    state.storeSearchResults.isEmpty() ->
                        Text(
                            text = stringResource(Res.string.sign_up_store_search_empty),
                            style = KoinTheme.typography.regular14,
                            color = KoinTheme.colors.neutral500
                        )
                    else ->
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(
                                items = state.storeSearchResults,
                                key = StoreSearchResult::id
                            ) { store ->
                                StoreSearchItem(
                                    store = store,
                                    selected = store.id == state.selectedStoreId,
                                    onClick = { onStoreClick(store.id) }
                                )
                            }
                        }
                }
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onSelect,
                enabled = state.selectedStoreId != null,
                shape = KoinTheme.shapes.small,
                colors = signupButtonColors(),
                contentPadding = PaddingValues(12.dp)
            ) {
                Text(
                    text = stringResource(Res.string.common_next),
                    style = KoinTheme.typography.medium15
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
private fun StoreSearchItem(
    store: StoreSearchResult,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier =
        modifier
            .fillMaxWidth()
            .border(
                width = if (selected) 1.5.dp else 1.dp,
                color = if (selected) KoinTheme.colors.primary500 else KoinTheme.colors.neutral300,
                shape = RoundedCornerShape(6.dp)
            ).noRippleClickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = store.name,
            style = KoinTheme.typography.medium15,
            color = KoinTheme.colors.neutral800,
            modifier = Modifier.weight(1f)
        )
        StoreAvailability(
            text = stringResource(Res.string.home_delivery),
            available = store.isDeliveryAvailable
        )
        StoreAvailability(
            text = stringResource(Res.string.home_card),
            available = store.isCardAvailable
        )
        StoreAvailability(
            text = stringResource(Res.string.home_bank_transfer),
            available = store.isBankTransferAvailable
        )
    }
}

@Composable
private fun StoreAvailability(
    text: String,
    available: Boolean,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = KoinTheme.typography.regular12,
        color = if (available) KoinTheme.colors.primary500 else KoinTheme.colors.neutral400,
        modifier = modifier
    )
}
