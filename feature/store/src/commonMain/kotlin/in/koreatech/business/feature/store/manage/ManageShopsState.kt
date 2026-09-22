package `in`.koreatech.business.feature.store.manage

import `in`.koreatech.business.domain.model.store.OwnerShop
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ManageShopsState(
    val shops: ImmutableList<OwnerShop> = persistentListOf(),
    val selectedShopId: Int? = null,
    val isLoading: Boolean = false
)
