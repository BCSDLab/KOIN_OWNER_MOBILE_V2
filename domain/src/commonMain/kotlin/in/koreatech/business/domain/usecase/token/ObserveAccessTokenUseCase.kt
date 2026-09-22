package `in`.koreatech.business.domain.usecase.token

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.repository.TokenRepository

@Inject
class ObserveAccessTokenUseCase(
    private val repository: TokenRepository
) {
    operator fun invoke() = repository.observeAccessToken()
}
