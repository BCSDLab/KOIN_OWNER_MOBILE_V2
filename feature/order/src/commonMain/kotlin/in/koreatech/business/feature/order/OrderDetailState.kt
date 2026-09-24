package `in`.koreatech.business.feature.order

import `in`.koreatech.business.domain.model.order.OwnerOrderDetail

data class OrderDetailState(
    val order: OwnerOrderDetail? = null,
    val isLoading: Boolean = true,
    val hasError: Boolean = false
)
