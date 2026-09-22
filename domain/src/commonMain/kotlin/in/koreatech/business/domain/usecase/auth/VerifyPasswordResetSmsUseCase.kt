package `in`.koreatech.business.domain.usecase.auth

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.repository.AuthRepository

@Inject
class VerifyPasswordResetSmsUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        phoneNumber: String,
        code: String
    ): Result<Unit> = repository.verifyPasswordResetSms(phoneNumber, code)
}
