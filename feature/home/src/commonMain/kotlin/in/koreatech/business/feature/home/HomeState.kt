package `in`.koreatech.business.feature.home

import `in`.koreatech.business.domain.model.store.OwnerShop

data class HomeState(
    val shop: OwnerShop? = null,
    val isLoading: Boolean = false
)
