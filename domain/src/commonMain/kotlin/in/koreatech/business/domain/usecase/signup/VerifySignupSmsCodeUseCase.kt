package `in`.koreatech.business.domain.usecase.signup

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.repository.SignupRepository

@Inject
class VerifySignupSmsCodeUseCase(
    private val repository: SignupRepository
) {
    suspend operator fun invoke(
        phoneNumber: String,
        code: String
    ): Result<String> = repository.verifySmsCode(phoneNumber, code)
}
