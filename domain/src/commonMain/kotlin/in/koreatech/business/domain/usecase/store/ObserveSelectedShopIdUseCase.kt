package `in`.koreatech.business.domain.usecase.store

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.repository.SelectedShopRepository
import kotlinx.coroutines.flow.Flow

@Inject
class ObserveSelectedShopIdUseCase(
    private val selectedShopRepository: SelectedShopRepository
) {
    operator fun invoke(): Flow<Int?> = selectedShopRepository.observeSelectedShopId()
}
