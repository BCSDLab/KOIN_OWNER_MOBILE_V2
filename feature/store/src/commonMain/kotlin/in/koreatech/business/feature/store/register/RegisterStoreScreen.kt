package `in`.koreatech.business.feature.store.register

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import dev.zacsweers.metrox.viewmodel.assistedMetroViewModel
import `in`.koreatech.business.core.designsystem.component.KoinErrorContent
import `in`.koreatech.business.core.designsystem.component.KoinLoadingContent
import `in`.koreatech.business.core.designsystem.component.topbar.KoinTopAppBar
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_add_symbol
import `in`.koreatech.business.core.designsystem.generated.resources.error_shop_load
import `in`.koreatech.business.core.designsystem.generated.resources.error_store_image_upload
import `in`.koreatech.business.core.designsystem.generated.resources.error_store_save
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_edit_title
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_intro_basic
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_intro_category
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_intro_confirm
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_intro_description
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_intro_operation
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_intro_title
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_start
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_title
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.core.viewmodel.rememberSavedStateViewModelCreationExtras
import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.jetbrains.compose.resources.stringResource
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

private sealed interface RegisterStoreRoute : NavKey {
    @Serializable
    data object Start : RegisterStoreRoute

    @Serializable
    data object Category : RegisterStoreRoute

    @Serializable
    data object BasicInfo : RegisterStoreRoute

    @Serializable
    data object DetailInfo : RegisterStoreRoute

    @Serializable
    data object Confirm : RegisterStoreRoute

    @Serializable
    data object Complete : RegisterStoreRoute
}

private val registerStoreSavedStateConfiguration =
    SavedStateConfiguration {
        serializersModule =
            SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(RegisterStoreRoute.Start::class)
                    subclass(RegisterStoreRoute.Category::class)
                    subclass(RegisterStoreRoute.BasicInfo::class)
                    subclass(RegisterStoreRoute.DetailInfo::class)
                    subclass(RegisterStoreRoute.Confirm::class)
                    subclass(RegisterStoreRoute.Complete::class)
                }
            }
    }

@Composable
fun RegisterStoreScreen(
    onBack: () -> Unit,
    onComplete: () -> Unit = onBack,
    shopId: Int? = null,
    modifier: Modifier = Modifier,
    viewModel: RegisterStoreViewModel = registerStoreViewModel(shopId)
) {
    val backStack =
        rememberNavBackStack(
            registerStoreSavedStateConfiguration,
            if (shopId == null) RegisterStoreRoute.Start else RegisterStoreRoute.Category
        )
    val state by viewModel.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val imageUploadErrorMessage = stringResource(Res.string.error_store_image_upload)
    val saveErrorMessage = stringResource(Res.string.error_store_save)
    val navigateBack = {
        if (backStack.size > 1) backStack.removeLastOrNull() else onBack()
        Unit
    }
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is RegisterStoreSideEffect.ShowError ->
                when (sideEffect.error) {
                    RegisterStoreError.ImageUpload -> snackbarHostState.showSnackbar(imageUploadErrorMessage)
                    RegisterStoreError.Save -> snackbarHostState.showSnackbar(saveErrorMessage)
                    RegisterStoreError.Load -> Unit
                }
            RegisterStoreSideEffect.NavigateToCategory -> backStack.add(RegisterStoreRoute.Category)
            RegisterStoreSideEffect.NavigateToBasicInfo -> backStack.add(RegisterStoreRoute.BasicInfo)
            RegisterStoreSideEffect.NavigateToDetailInfo -> backStack.add(RegisterStoreRoute.DetailInfo)
            RegisterStoreSideEffect.NavigateToConfirm -> backStack.add(RegisterStoreRoute.Confirm)
            RegisterStoreSideEffect.NavigateToComplete -> backStack.add(RegisterStoreRoute.Complete)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        when {
            state.isLoading -> KoinLoadingContent()
            state.error == RegisterStoreError.Load ->
                KoinErrorContent(
                    message = stringResource(Res.string.error_shop_load),
                    onRetry = viewModel::retry
                )
            else ->
                RegisterStoreScreenImpl(
                    state = state,
                    backStack = backStack,
                    onCategorySelect = viewModel::onCategorySelected,
                    onStoreNameChange = viewModel::onStoreNameChanged,
                    onAddressChange = viewModel::onAddressChanged,
                    onUploadImage = viewModel::onImageUploadRequested,
                    onImageSelectionFailed = viewModel::onImageSelectionFailed,
                    onRemoveImage = viewModel::onImageRemoved,
                    onPhoneNumberChange = viewModel::onPhoneNumberChanged,
                    onDeliveryFeeChange = viewModel::onDeliveryFeeChanged,
                    onOtherInfoChange = viewModel::onOtherInfoChanged,
                    onOperatingTimesChange = viewModel::onOperatingTimesChanged,
                    onDeliveryAvailabilityChange = viewModel::onDeliveryAvailabilityChanged,
                    onCardAvailabilityChange = viewModel::onCardAvailabilityChanged,
                    onBankTransferAvailabilityChange = viewModel::onBankTransferAvailabilityChanged,
                    onBack = navigateBack,
                    onStart = viewModel::onRegistrationStarted,
                    onCategoryNext = viewModel::onCategoryCompleted,
                    onBasicInfoNext = viewModel::onBasicInfoCompleted,
                    onDetailInfoNext = viewModel::onDetailInfoCompleted,
                    onConfirm = viewModel::onRegistrationConfirmed,
                    onComplete = onComplete
                )
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
        )
    }
}

@Composable
private fun registerStoreViewModel(shopId: Int?): RegisterStoreViewModel {
    val extras =
        rememberSavedStateViewModelCreationExtras(shopId) {
            shopId?.let { putInt(RegisterStoreViewModel.SHOP_ID_KEY, it) }
        }
    return assistedMetroViewModel(extras = extras)
}

@Composable
internal fun RegisterStoreScreenImpl(
    state: RegisterStoreState,
    backStack: MutableList<NavKey>,
    onCategorySelect: (Int) -> Unit,
    onStoreNameChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onUploadImage: (String, String, ByteArray) -> Unit,
    onImageSelectionFailed: () -> Unit,
    onRemoveImage: (Int) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onDeliveryFeeChange: (String) -> Unit,
    onOtherInfoChange: (String) -> Unit,
    onOperatingTimesChange: (ImmutableList<RegisterStoreOperatingTime>) -> Unit,
    onDeliveryAvailabilityChange: (Boolean) -> Unit,
    onCardAvailabilityChange: (Boolean) -> Unit,
    onBankTransferAvailabilityChange: (Boolean) -> Unit,
    onBack: () -> Unit,
    onStart: () -> Unit,
    onCategoryNext: () -> Unit,
    onBasicInfoNext: () -> Unit,
    onDetailInfoNext: () -> Unit,
    onConfirm: () -> Unit,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val screenTitle =
        stringResource(
            if (state.shopId == null) Res.string.register_store_title else Res.string.register_store_edit_title
        )
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = onBack,
        entryProvider =
        entryProvider {
            entry<RegisterStoreRoute.Start> {
                RegisterStoreStartScreen(onBack = onBack, onStartClick = onStart)
            }
            entry<RegisterStoreRoute.Category> {
                RegisterStoreCategoryScreen(
                    title = screenTitle,
                    categories = state.categories,
                    selectedCategoryId = state.selectedCategoryId,
                    onCategorySelect = onCategorySelect,
                    onBack = onBack,
                    onNext = onCategoryNext
                )
            }
            entry<RegisterStoreRoute.BasicInfo> {
                RegisterStoreBasicInfoScreen(
                    title = screenTitle,
                    state = state,
                    onStoreNameChange = onStoreNameChange,
                    onAddressChange = onAddressChange,
                    onUploadImage = onUploadImage,
                    onImageSelectionFailed = onImageSelectionFailed,
                    onRemoveImage = onRemoveImage,
                    onBack = onBack,
                    onNext = onBasicInfoNext
                )
            }
            entry<RegisterStoreRoute.DetailInfo> {
                RegisterStoreDetailInfoScreen(
                    title = screenTitle,
                    state = state,
                    onPhoneNumberChange = onPhoneNumberChange,
                    onDeliveryFeeChange = onDeliveryFeeChange,
                    onOtherInfoChange = onOtherInfoChange,
                    onOperatingTimesChange = onOperatingTimesChange,
                    onDeliveryAvailabilityChange = onDeliveryAvailabilityChange,
                    onCardAvailabilityChange = onCardAvailabilityChange,
                    onBankTransferAvailabilityChange = onBankTransferAvailabilityChange,
                    onBack = onBack,
                    onNext = onDetailInfoNext
                )
            }
            entry<RegisterStoreRoute.Confirm> {
                RegisterStoreConfirmScreen(
                    state = state,
                    title = screenTitle,
                    onBack = onBack,
                    onConfirm = onConfirm
                )
            }
            entry<RegisterStoreRoute.Complete> {
                RegisterStoreCompleteScreen(isEditing = state.shopId != null, onComplete = onComplete)
            }
        }
    )
}

@Composable
private fun RegisterStoreStartScreen(
    onBack: () -> Unit,
    onStartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = KoinTheme.colors.neutral75,
        topBar = {
            KoinTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(Res.string.register_store_title), style = KoinTheme.typography.medium18) },
                onNavigationIconClick = onBack
            )
        }
    ) { contentPadding ->
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier =
                Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(KoinTheme.colors.primary100),
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(Res.string.common_add_symbol), style = KoinTheme.typography.bold20, color = KoinTheme.colors.primary600)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                stringResource(Res.string.register_store_intro_title),
                style = KoinTheme.typography.bold20,
                color = KoinTheme.colors.neutral800,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                stringResource(Res.string.register_store_intro_description),
                style = KoinTheme.typography.regular15,
                color = KoinTheme.colors.neutral500,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(40.dp))
            Column(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(KoinTheme.colors.neutral0)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                listOf(
                    stringResource(Res.string.register_store_intro_category),
                    stringResource(Res.string.register_store_intro_basic),
                    stringResource(Res.string.register_store_intro_operation),
                    stringResource(Res.string.register_store_intro_confirm)
                ).forEachIndexed { index, text -> RegisterStoreGuideItem("${index + 1}", text) }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onStartClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors =
                ButtonDefaults.buttonColors(
                    containerColor = KoinTheme.colors.primary500,
                    contentColor = KoinTheme.colors.neutral0
                ),
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                Text(stringResource(Res.string.register_store_start), style = KoinTheme.typography.medium15)
            }
        }
    }
}

@Composable
private fun RegisterStoreGuideItem(
    step: String,
    text: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier =
            Modifier
                .size(28.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(KoinTheme.colors.primary100),
            contentAlignment = Alignment.Center
        ) {
            Text(step, style = KoinTheme.typography.bold13, color = KoinTheme.colors.primary600)
        }
        Text(
            text,
            modifier = Modifier.padding(start = 12.dp),
            style = KoinTheme.typography.medium15,
            color = KoinTheme.colors.neutral700
        )
    }
}
