package `in`.koreatech.business.domain.usecase.auth

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.repository.AuthRepository
import `in`.koreatech.business.domain.repository.TokenRepository
import `in`.koreatech.business.domain.service.PasswordHasher

@Inject
class SignInUseCase(
    private val repository: AuthRepository,
    private val tokenRepository: TokenRepository,
    private val passwordHasher: PasswordHasher
) {
    suspend operator fun invoke(
        phoneNumber: String,
        password: String
    ): Result<Unit> = repository.signIn(phoneNumber, passwordHasher.hash(password))
        .mapCatching { tokens ->
            tokenRepository.saveTokens(tokens.accessToken, tokens.refreshToken)
        }
}
