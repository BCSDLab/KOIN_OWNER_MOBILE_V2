package `in`.koreatech.business.domain.usecase.signup

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.model.signup.OwnerRegistration
import `in`.koreatech.business.domain.repository.SignupRepository
import `in`.koreatech.business.domain.service.PasswordHasher

@Inject
class RegisterOwnerUseCase(
    private val repository: SignupRepository,
    private val passwordHasher: PasswordHasher
) {
    suspend operator fun invoke(
        registration: OwnerRegistration,
        verificationToken: String
    ): Result<Unit> =
        repository.registerOwner(
            registration.copy(password = passwordHasher.hash(registration.password)),
            verificationToken
        )
}
