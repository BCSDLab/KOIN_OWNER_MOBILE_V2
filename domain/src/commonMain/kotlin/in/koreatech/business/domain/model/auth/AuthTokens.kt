package `in`.koreatech.business.domain.model.auth

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String
)
