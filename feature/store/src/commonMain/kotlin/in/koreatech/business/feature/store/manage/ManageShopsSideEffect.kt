package `in`.koreatech.business.feature.store.manage

sealed interface ManageShopsSideEffect {
    data object ShopLoadFailed : ManageShopsSideEffect
}
