package `in`.koreatech.business.domain.usecase.auth

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.repository.AuthRepository

@Inject
class SendPasswordResetSmsUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(phoneNumber: String): Result<Unit> = repository.sendPasswordResetSms(phoneNumber)
}
