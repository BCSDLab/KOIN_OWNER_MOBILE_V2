package `in`.koreatech.business.feature.store.register

import androidx.compose.foundation.text.input.TextFieldState
import `in`.koreatech.business.feature.store.register.model.RegisterStoreAddress
import `in`.koreatech.business.feature.store.register.model.RegisterStoreCategory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableSet

data class RegisterStoreState(
    val storeNameTextFieldState: TextFieldState = TextFieldState(),
    val addressTextFieldState: TextFieldState = TextFieldState(),
    val addressSearchTextFieldState: TextFieldState = TextFieldState(),
    val phoneNumberTextFieldState: TextFieldState = TextFieldState(),
    val deliveryFeeTextFieldState: TextFieldState = TextFieldState(),
    val otherInfoTextFieldState: TextFieldState = TextFieldState(),
    val shopId: Int? = null,
    val categories: ImmutableList<RegisterStoreCategory> = persistentListOf(),
    val selectedCategoryId: Int? = null,
    val addressSearchResults: ImmutableList<RegisterStoreAddress> = persistentListOf(),
    val hasAddressSearchResult: Boolean = false,
    val isAddressSearching: Boolean = false,
    val isAddressSearchError: Boolean = false,
    val operatingTimes: ImmutableList<RegisterStoreOperatingTime> = persistentListOf(
        RegisterStoreOperatingTime(
            days = RegisterStoreDay.entries.toImmutableSet(),
            openingTime = "09:00",
            closingTime = "22:00",
            is24Hours = false
        )
    ),
    val isDeliveryAvailable: Boolean = false,
    val isCardAvailable: Boolean = false,
    val isBankTransferAvailable: Boolean = false,
    val imageUrls: ImmutableList<String> = persistentListOf(),
    val pendingImageCount: Int = 0,
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val error: RegisterStoreError? = null
) {
    val storeName: String get() = storeNameTextFieldState.text.toString()
    val address: String get() = addressTextFieldState.text.toString()
    val phoneNumber: String get() = phoneNumberTextFieldState.text.toString()
    val deliveryFee: String get() = deliveryFeeTextFieldState.text.toString()
    val otherInfo: String get() = otherInfoTextFieldState.text.toString()

    val isCategoryValid: Boolean
        get() = selectedCategoryId != null

    val isBasicInfoValid: Boolean
        get() = imageUrls.isNotEmpty() && storeName.isNotBlank() && address.isNotBlank()

    val isDetailInfoValid: Boolean
        get() = phoneNumber.isNotBlank() && deliveryFee.isNotBlank() && otherInfo.isNotBlank()

    val isUploading: Boolean
        get() = pendingImageCount > 0
}
