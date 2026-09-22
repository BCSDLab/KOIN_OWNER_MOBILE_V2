package `in`.koreatech.business.domain.usecase.auth

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.repository.AuthRepository
import `in`.koreatech.business.domain.repository.TokenRepository

@Inject
class DeleteOwnerUseCase(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(): Result<Unit> = authRepository.deleteOwner().onSuccess { tokenRepository.clearTokens() }
}
