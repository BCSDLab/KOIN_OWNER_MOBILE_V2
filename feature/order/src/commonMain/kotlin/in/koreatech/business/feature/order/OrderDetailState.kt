package `in`.koreatech.business.feature.order

import `in`.koreatech.business.feature.order.model.OrderDetailUiModel

data class OrderDetailState(
    val order: OrderDetailUiModel? = null,
    val isLoading: Boolean = true,
    val hasError: Boolean = false
)
