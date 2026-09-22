package `in`.koreatech.business.domain.usecase.signup

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.repository.SignupRepository

@Inject
class CheckCompanyNumberUseCase(
    private val repository: SignupRepository
) {
    suspend operator fun invoke(companyNumber: String): Result<Unit> = repository.checkCompanyNumber(companyNumber)
}
