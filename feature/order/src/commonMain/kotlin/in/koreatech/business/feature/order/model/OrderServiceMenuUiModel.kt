package `in`.koreatech.business.feature.order.model

import `in`.koreatech.business.domain.model.store.OwnerMenu

data class OrderServiceMenuUiModel(
    val id: Int,
    val name: String,
    val minimumPrice: Int?
)

internal fun OwnerMenu.toOrderServiceMenuUiModel(): OrderServiceMenuUiModel = OrderServiceMenuUiModel(
    id = id,
    name = name,
    minimumPrice = prices.minOfOrNull { it.price }
)
