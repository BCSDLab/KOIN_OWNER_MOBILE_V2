package `in`.koreatech.business.feature.store.manage

import `in`.koreatech.business.feature.store.manage.model.ManageShopItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ManageShopsState(
    val shops: ImmutableList<ManageShopItem> = persistentListOf(),
    val selectedShopId: Int? = null,
    val isLoading: Boolean = false
)
