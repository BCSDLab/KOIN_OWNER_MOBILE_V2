package `in`.koreatech.business.feature.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import dev.zacsweers.metrox.viewmodel.metroViewModel
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.feature.signup.attachment.SignUpAttachmentScreen
import `in`.koreatech.business.feature.signup.businessnumber.SignUpBusinessNumberScreen
import `in`.koreatech.business.feature.signup.complete.SignUpCompleteScreen
import `in`.koreatech.business.feature.signup.password.SignUpPasswordScreen
import `in`.koreatech.business.feature.signup.search.SignUpStoreSearchScreen
import `in`.koreatech.business.feature.signup.store.SignUpStoreScreen
import `in`.koreatech.business.feature.signup.term.SignUpTermScreen
import `in`.koreatech.business.feature.signup.verification.SignUpVerificationScreen
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

private sealed interface SignupRoute : NavKey {
    @Serializable
    data object Terms : SignupRoute

    @Serializable
    data object Account : SignupRoute

    @Serializable
    data object Password : SignupRoute

    @Serializable
    data object Business : SignupRoute

    @Serializable
    data object Store : SignupRoute

    @Serializable
    data object StoreSearch : SignupRoute

    @Serializable
    data object Attachments : SignupRoute

    @Serializable
    data object Complete : SignupRoute
}

private val signupSavedStateConfiguration = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(SignupRoute.Terms::class)
            subclass(SignupRoute.Account::class)
            subclass(SignupRoute.Password::class)
            subclass(SignupRoute.Business::class)
            subclass(SignupRoute.Store::class)
            subclass(SignupRoute.StoreSearch::class)
            subclass(SignupRoute.Attachments::class)
            subclass(SignupRoute.Complete::class)
        }
    }
}

@Composable
fun SignupScreen(
    onBack: () -> Unit,
    onComplete: () -> Unit,
    viewModel: SignupViewModel = metroViewModel()
) {
    val state by viewModel.collectAsState()
    val backStack = rememberNavBackStack(signupSavedStateConfiguration, SignupRoute.Terms)
    val navigateBack = {
        if (backStack.size > 1) backStack.removeLastOrNull() else onBack()
        Unit
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            SignupSideEffect.NavigateToAccount -> backStack.add(SignupRoute.Account)
            SignupSideEffect.NavigateToPassword -> backStack.add(SignupRoute.Password)
            SignupSideEffect.NavigateToBusiness -> backStack.add(SignupRoute.Business)
            SignupSideEffect.NavigateToStore -> backStack.add(SignupRoute.Store)
            SignupSideEffect.NavigateBackFromStoreSearch -> backStack.removeLastOrNull()
            SignupSideEffect.NavigateToAttachments -> backStack.add(SignupRoute.Attachments)
            SignupSideEffect.NavigateToComplete -> backStack.add(SignupRoute.Complete)
            SignupSideEffect.CompleteSignup -> onComplete()
        }
    }

    SignupScreenImpl(
        state = state,
        backStack = backStack,
        onBack = navigateBack,
        onPrivacyTermCheckChange = viewModel::setPrivacyTermsAgreed,
        onKoinTermCheckChange = viewModel::setServiceTermsAgreed,
        onMarketingTermCheckChange = viewModel::setMarketingTermsAgreed,
        onAllTermCheckChange = viewModel::setAllTermsAgreed,
        onNameChange = viewModel::updateName,
        onPhoneNumberChange = viewModel::updatePhoneNumber,
        onVerificationCodeChange = viewModel::updateVerificationCode,
        onSendVerificationCode = viewModel::sendVerificationCode,
        onVerifyCode = viewModel::verifyCode,
        onPasswordChange = viewModel::updatePassword,
        onPasswordConfirmChange = viewModel::updatePasswordConfirmation,
        onBusinessNumberChange = viewModel::updateBusinessNumber,
        onStoreNameChange = viewModel::updateStoreName,
        onStorePhoneNumberChange = viewModel::updateStorePhoneNumber,
        onSearchStore = { backStack.add(SignupRoute.StoreSearch) },
        onStoreSearchQueryChange = viewModel::updateStoreSearchQuery,
        onStoreSearchResultClick = viewModel::selectStore,
        onStoreSearchSelect = viewModel::applySelectedStore,
        onLoadStores = viewModel::loadStores,
        onUploadFile = viewModel::uploadFile,
        onFileSelectionFailed = viewModel::onFileSelectionFailed,
        onDeleteFile = viewModel::removeFile,
        onTermsNext = viewModel::navigateToAccount,
        onAccountNext = viewModel::navigateToPassword,
        onPasswordNext = viewModel::navigateToBusiness,
        onBusinessNext = viewModel::checkBusinessNumber,
        onStoreNext = viewModel::navigateToAttachments,
        onRegister = viewModel::register,
        onComplete = viewModel::completeSignup
    )
}

@Composable
internal fun SignupScreenImpl(
    state: SignupState,
    backStack: MutableList<NavKey>,
    onBack: () -> Unit,
    onPrivacyTermCheckChange: (Boolean) -> Unit,
    onKoinTermCheckChange: (Boolean) -> Unit,
    onMarketingTermCheckChange: (Boolean) -> Unit,
    onAllTermCheckChange: (Boolean) -> Unit,
    onNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onVerificationCodeChange: (String) -> Unit,
    onSendVerificationCode: () -> Unit,
    onVerifyCode: () -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmChange: (String) -> Unit,
    onBusinessNumberChange: (String) -> Unit,
    onStoreNameChange: (String) -> Unit,
    onStorePhoneNumberChange: (String) -> Unit,
    onSearchStore: () -> Unit,
    onStoreSearchQueryChange: (String) -> Unit,
    onStoreSearchResultClick: (Int) -> Unit,
    onStoreSearchSelect: () -> Unit,
    onLoadStores: () -> Unit,
    onUploadFile: (String, String, ByteArray) -> Unit,
    onFileSelectionFailed: () -> Unit,
    onDeleteFile: (Int) -> Unit,
    onTermsNext: () -> Unit,
    onAccountNext: () -> Unit,
    onPasswordNext: () -> Unit,
    onBusinessNext: () -> Unit,
    onStoreNext: () -> Unit,
    onRegister: () -> Unit,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    KoinTheme {
        NavDisplay(
            modifier = modifier,
            backStack = backStack,
            onBack = onBack,
            entryProvider = entryProvider {
                entry<SignupRoute.Terms> {
                    SignUpTermScreen(
                        state = state,
                        onBack = onBack,
                        onPrivacyTermCheckChange = onPrivacyTermCheckChange,
                        onKoinTermCheckChange = onKoinTermCheckChange,
                        onMarketingTermCheckChange = onMarketingTermCheckChange,
                        onAllTermCheckChange = onAllTermCheckChange,
                        navigateToNextScreen = onTermsNext
                    )
                }
                entry<SignupRoute.Account> {
                    SignUpVerificationScreen(
                        state = state,
                        onBack = onBack,
                        onNameChange = onNameChange,
                        onPhoneNumberChange = onPhoneNumberChange,
                        onVerificationCodeChange = onVerificationCodeChange,
                        onSendVerificationCode = onSendVerificationCode,
                        onVerifyCode = onVerifyCode,
                        navigateToNextScreen = onAccountNext
                    )
                }
                entry<SignupRoute.Password> {
                    SignUpPasswordScreen(
                        state = state,
                        onBack = onBack,
                        onPasswordChange = onPasswordChange,
                        onPasswordConfirmChange = onPasswordConfirmChange,
                        navigateToNextScreen = onPasswordNext
                    )
                }
                entry<SignupRoute.Business> {
                    SignUpBusinessNumberScreen(
                        state = state,
                        onBack = onBack,
                        onBusinessNumberChange = onBusinessNumberChange,
                        navigateToNextScreen = onBusinessNext
                    )
                }
                entry<SignupRoute.Store> {
                    SignUpStoreScreen(
                        state = state,
                        onBack = onBack,
                        onStoreNameChange = onStoreNameChange,
                        onStorePhoneNumberChange = onStorePhoneNumberChange,
                        onSearchStore = onSearchStore,
                        navigateToNextScreen = onStoreNext
                    )
                }
                entry<SignupRoute.StoreSearch> {
                    SignUpStoreSearchScreen(
                        state = state,
                        onBack = onBack,
                        onQueryChange = onStoreSearchQueryChange,
                        onStoreClick = onStoreSearchResultClick,
                        onSelect = onStoreSearchSelect,
                        onLoad = onLoadStores
                    )
                }
                entry<SignupRoute.Attachments> {
                    SignUpAttachmentScreen(
                        state = state,
                        onBack = onBack,
                        onUploadFile = onUploadFile,
                        onFileSelectionFailed = onFileSelectionFailed,
                        onDeleteFile = onDeleteFile,
                        navigateToNextScreen = onRegister
                    )
                }
                entry<SignupRoute.Complete> {
                    SignUpCompleteScreen(
                        onBack = onBack,
                        onComplete = onComplete
                    )
                }
            }
        )
    }
}
