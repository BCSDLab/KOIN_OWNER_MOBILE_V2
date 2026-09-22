package `in`.koreatech.business.domain.usecase.store

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.model.store.OwnerShop
import `in`.koreatech.business.domain.repository.SelectedShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

@Inject
class ObserveSelectedShopUseCase(
    private val selectedShopRepository: SelectedShopRepository,
    private val getSelectedShopUseCase: GetSelectedShopUseCase
) {
    operator fun invoke(): Flow<Result<OwnerShop?>> =
        flow {
            selectedShopRepository.observeSelectedShopId().collect {
                emitAll(getSelectedShopUseCase())
            }
        }
}
