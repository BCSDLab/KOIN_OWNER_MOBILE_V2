package `in`.koreatech.business.feature.home

import `in`.koreatech.business.feature.home.model.HomeShop

data class HomeState(
    val shop: HomeShop? = null,
    val isLoading: Boolean = false
)
