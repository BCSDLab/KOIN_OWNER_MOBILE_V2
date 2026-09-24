package `in`.koreatech.business.feature.order

import `in`.koreatech.business.domain.model.order.OwnerOrderCategory
import `in`.koreatech.business.feature.order.model.OrderUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class OrderState(
    val orderableShopId: Int? = null,
    val category: OwnerOrderCategory = OwnerOrderCategory.NEW,
    val orders: ImmutableList<OrderUiModel> = persistentListOf(),
    val isLoading: Boolean = true,
    val hasError: Boolean = false
)
