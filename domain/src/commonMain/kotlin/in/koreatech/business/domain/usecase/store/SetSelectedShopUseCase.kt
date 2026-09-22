package `in`.koreatech.business.domain.usecase.store

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.repository.SelectedShopRepository

@Inject
class SetSelectedShopUseCase(
    private val selectedShopRepository: SelectedShopRepository
) {
    operator fun invoke(shopId: Int?) {
        shopId?.let(selectedShopRepository::saveSelectedShopId)
            ?: selectedShopRepository.clearSelectedShopId()
    }
}
