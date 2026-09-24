package `in`.koreatech.business.feature.store.manage.model

import `in`.koreatech.business.domain.model.store.OwnerShop

data class ManageShopItem(
    val id: Int,
    val name: String,
    val address: String?
)

internal fun OwnerShop.toManageShopItem() = ManageShopItem(
    id = id,
    name = name,
    address = address
)
