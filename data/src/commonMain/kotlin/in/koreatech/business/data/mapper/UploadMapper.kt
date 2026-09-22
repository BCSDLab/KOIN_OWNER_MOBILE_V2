package `in`.koreatech.business.data.mapper

import `in`.koreatech.business.data.response.upload.UploadUrlResponse
import `in`.koreatech.business.domain.model.upload.PreSignedUrl

internal fun UploadUrlResponse.toPreSignedUrl(): PreSignedUrl =
    PreSignedUrl(
        fileUrl = fileUrl,
        preSignedUrl = preSignedUrl
    )
