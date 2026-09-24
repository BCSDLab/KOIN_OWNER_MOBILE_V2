package `in`.koreatech.business.feature.signup.model

import `in`.koreatech.business.domain.model.store.StoreSearchResult

data class SignupStoreSearchResult(
    val id: Int,
    val name: String,
    val phone: String,
    val isDeliveryAvailable: Boolean,
    val isCardAvailable: Boolean,
    val isBankTransferAvailable: Boolean
)

data class SignupAttachment(
    val url: String,
    val title: String
)

data class SignupStoreUrl(
    val uri: String,
    val resultUrl: String,
    val fileName: String,
    val mediaType: String,
    val preSignedUrl: String,
    val fileSize: Long
)

internal fun StoreSearchResult.toSignupStoreSearchResult() = SignupStoreSearchResult(
    id = id,
    name = name,
    phone = phone,
    isDeliveryAvailable = isDeliveryAvailable,
    isCardAvailable = isCardAvailable,
    isBankTransferAvailable = isBankTransferAvailable
)
