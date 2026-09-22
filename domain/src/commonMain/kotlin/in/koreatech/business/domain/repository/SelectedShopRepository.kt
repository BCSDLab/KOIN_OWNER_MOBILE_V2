package `in`.koreatech.business.domain.repository

import kotlinx.coroutines.flow.Flow

interface SelectedShopRepository {
    fun observeSelectedShopId(): Flow<Int?>

    fun saveSelectedShopId(shopId: Int)

    fun clearSelectedShopId()
}
