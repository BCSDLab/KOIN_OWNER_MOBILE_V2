package `in`.koreatech.business.domain.usecase.store

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.model.store.OwnerShop
import `in`.koreatech.business.domain.repository.OwnerShopRepository
import `in`.koreatech.business.domain.repository.SelectedShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

@Inject
class GetSelectedShopUseCase(
    private val ownerShopRepository: OwnerShopRepository,
    private val selectedShopRepository: SelectedShopRepository
) {
    operator fun invoke(): Flow<Result<OwnerShop?>> = flow {
        val selectedShopId = selectedShopRepository.observeSelectedShopId().first()
        emit(
            ownerShopRepository.getOwnerShops().map { shops ->
                val selectedShop = shops.firstOrNull { it.id == selectedShopId }
                    ?: shops.firstOrNull()
                when {
                    selectedShop == null -> selectedShopRepository.clearSelectedShopId()
                    selectedShop.id != selectedShopId -> {
                        selectedShopRepository.saveSelectedShopId(selectedShop.id)
                    }
                }
                selectedShop
            }
        )
    }
}
