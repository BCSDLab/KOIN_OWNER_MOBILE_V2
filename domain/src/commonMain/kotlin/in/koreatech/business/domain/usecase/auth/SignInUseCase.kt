package `in`.koreatech.business.domain.usecase.auth

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.model.auth.AuthTokens
import `in`.koreatech.business.domain.repository.AuthRepository
import `in`.koreatech.business.domain.service.PasswordHasher

@Inject
class SignInUseCase(
    private val repository: AuthRepository,
    private val passwordHasher: PasswordHasher
) {
    suspend operator fun invoke(
        phoneNumber: String,
        password: String
    ): Result<AuthTokens> = repository.signIn(phoneNumber, passwordHasher.hash(password))
}
