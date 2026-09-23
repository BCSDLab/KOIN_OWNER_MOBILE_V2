package `in`.koreatech.business.feature.store.register

import `in`.koreatech.business.domain.model.store.ShopCategory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableSet

data class RegisterStoreState(
    val shopId: Int? = null,
    val categories: ImmutableList<ShopCategory> = persistentListOf(),
    val selectedCategoryId: Int? = null,
    val storeName: String = "",
    val address: String = "",
    val phoneNumber: String = "",
    val deliveryFee: String = "",
    val operatingTimes: ImmutableList<RegisterStoreOperatingTime> = persistentListOf(
        RegisterStoreOperatingTime(
            days = RegisterStoreDay.entries.toImmutableSet(),
            openingTime = "09:00",
            closingTime = "22:00",
            is24Hours = false
        )
    ),
    val otherInfo: String = "",
    val isDeliveryAvailable: Boolean = false,
    val isCardAvailable: Boolean = false,
    val isBankTransferAvailable: Boolean = false,
    val imageUrls: ImmutableList<String> = persistentListOf(),
    val isLoading: Boolean = true,
    val isUploading: Boolean = false,
    val isSaving: Boolean = false,
    val error: RegisterStoreError? = null
) {
    val isCategoryValid: Boolean
        get() = selectedCategoryId != null

    val isBasicInfoValid: Boolean
        get() = imageUrls.isNotEmpty() && storeName.isNotBlank() && address.isNotBlank()

    val isDetailInfoValid: Boolean
        get() = phoneNumber.isNotBlank() && deliveryFee.isNotBlank() && otherInfo.isNotBlank()
}
