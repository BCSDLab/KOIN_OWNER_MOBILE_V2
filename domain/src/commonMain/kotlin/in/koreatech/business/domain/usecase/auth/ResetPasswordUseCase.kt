package `in`.koreatech.business.domain.usecase.auth

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.repository.AuthRepository
import `in`.koreatech.business.domain.service.PasswordHasher

@Inject
class ResetPasswordUseCase(
    private val repository: AuthRepository,
    private val passwordHasher: PasswordHasher
) {
    suspend operator fun invoke(
        phoneNumber: String,
        password: String
    ): Result<Unit> = repository.resetPassword(phoneNumber, passwordHasher.hash(password))
}
