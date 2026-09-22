package `in`.koreatech.business.domain.usecase.signup

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.repository.SignupRepository

@Inject
class SendSignupSmsCodeUseCase(
    private val repository: SignupRepository
) {
    suspend operator fun invoke(phoneNumber: String): Result<Unit> =
        repository.checkAccount(phoneNumber).fold(
            onSuccess = { repository.requestSmsVerification(phoneNumber) },
            onFailure = { Result.failure(it) }
        )
}
