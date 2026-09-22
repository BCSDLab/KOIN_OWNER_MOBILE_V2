package `in`.koreatech.business.data.repository

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.data.source.local.SelectedShopLocalDataSource
import `in`.koreatech.business.domain.repository.SelectedShopRepository
import kotlinx.coroutines.flow.Flow

@ContributesBinding(AppScope::class)
@Inject
@SingleIn(AppScope::class)
class SelectedShopRepositoryImpl(
    private val selectedShopLocalDataSource: SelectedShopLocalDataSource
) : SelectedShopRepository {
    override fun observeSelectedShopId(): Flow<Int?> = selectedShopLocalDataSource.observeSelectedShopId()

    override fun saveSelectedShopId(shopId: Int) {
        selectedShopLocalDataSource.saveSelectedShopId(shopId)
    }

    override fun clearSelectedShopId() {
        selectedShopLocalDataSource.clearSelectedShopId()
    }
}
