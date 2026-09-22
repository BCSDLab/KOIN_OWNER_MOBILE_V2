package `in`.koreatech.business.domain.usecase.token

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.repository.TokenRepository

@Inject
class SignOutUseCase(
    private val repository: TokenRepository
) {
    suspend operator fun invoke() = repository.clearTokens()
}
