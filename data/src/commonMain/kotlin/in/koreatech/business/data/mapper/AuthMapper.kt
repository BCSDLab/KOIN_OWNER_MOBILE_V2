package `in`.koreatech.business.data.mapper

import `in`.koreatech.business.data.response.auth.OwnerLoginResponse
import `in`.koreatech.business.domain.model.auth.AuthTokens

internal fun OwnerLoginResponse.toAuthTokens(): AuthTokens =
    AuthTokens(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
