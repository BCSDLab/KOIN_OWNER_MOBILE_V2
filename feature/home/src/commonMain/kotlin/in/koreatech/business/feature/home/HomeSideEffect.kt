package `in`.koreatech.business.feature.home

sealed interface HomeSideEffect {
    data object ShopLoadFailed : HomeSideEffect
}
