package `in`.koreatech.business.data.source.local

import com.russhwolf.settings.Settings
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import `in`.koreatech.business.core.di.AppScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Inject
@SingleIn(AppScope::class)
class SelectedShopLocalDataSource(
    private val settings: Settings
) {
    private val selectedShopId = MutableStateFlow(settings.getIntOrNull(SELECTED_SHOP_ID))

    fun observeSelectedShopId(): Flow<Int?> = selectedShopId.asStateFlow()

    fun saveSelectedShopId(shopId: Int) {
        settings.putInt(SELECTED_SHOP_ID, shopId)
        selectedShopId.value = shopId
    }

    fun clearSelectedShopId() {
        settings.remove(SELECTED_SHOP_ID)
        selectedShopId.value = null
    }

    private companion object {
        const val SELECTED_SHOP_ID = "selected_shop_id"
    }
}
