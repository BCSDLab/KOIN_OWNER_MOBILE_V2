package `in`.koreatech.business.domain.usecase.store

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.model.store.OwnerShop
import `in`.koreatech.business.domain.repository.OwnerShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Inject
class GetOwnerShopsUseCase(
    private val repository: OwnerShopRepository
) {
    operator fun invoke(): Flow<Result<List<OwnerShop>>> =
        flow {
            emit(repository.getOwnerShops())
        }
}
