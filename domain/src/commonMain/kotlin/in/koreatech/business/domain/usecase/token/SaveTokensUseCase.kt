package `in`.koreatech.business.domain.usecase.token

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.repository.TokenRepository

@Inject
class SaveTokensUseCase(
    private val repository: TokenRepository
) {
    suspend operator fun invoke(
        accessToken: String,
        refreshToken: String
    ): Unit = repository.saveTokens(accessToken, refreshToken)
}
