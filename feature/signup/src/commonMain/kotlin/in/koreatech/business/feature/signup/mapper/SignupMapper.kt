package `in`.koreatech.business.feature.signup.mapper

import `in`.koreatech.business.domain.model.signup.OwnerRegistration
import `in`.koreatech.business.domain.util.formatBusinessNumber
import `in`.koreatech.business.feature.signup.SignupState
import `in`.koreatech.business.feature.signup.model.SignupStoreUrl
import kotlinx.collections.immutable.toImmutableList

internal fun SignupState.toOwnerRegistration() = OwnerRegistration(
    attachmentUrls = fileInfo.map(SignupStoreUrl::resultUrl).toImmutableList(),
    companyNumber = businessNumber.formatBusinessNumber(),
    name = name,
    password = password,
    phoneNumber = phoneNumber,
    shopNumber = storePhoneNumber,
    shopId = selectedStoreId,
    shopName = storeName
)

internal fun String.toStoreUrl(
    fileName: String,
    mediaType: String,
    fileSize: Long
) = SignupStoreUrl(
    uri = fileName,
    resultUrl = this,
    fileName = fileName,
    mediaType = mediaType,
    preSignedUrl = "",
    fileSize = fileSize
)
