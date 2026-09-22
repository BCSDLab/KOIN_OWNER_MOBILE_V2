package `in`.koreatech.business.data.mapper

import `in`.koreatech.business.data.request.signup.AttachmentUrlRequest
import `in`.koreatech.business.data.request.signup.OwnerRegisterRequest
import `in`.koreatech.business.domain.model.signup.OwnerRegistration

internal fun OwnerRegistration.toOwnerRegisterRequest(): OwnerRegisterRequest =
    OwnerRegisterRequest(
        attachmentUrls = attachmentUrls.map(::AttachmentUrlRequest),
        companyNumber = companyNumber,
        name = name,
        password = password,
        phoneNumber = phoneNumber,
        shopNumber = shopNumber,
        shopId = shopId,
        shopName = shopName
    )
