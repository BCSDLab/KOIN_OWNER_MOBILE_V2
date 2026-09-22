package `in`.koreatech.business.feature.store.register

import kotlinx.collections.immutable.ImmutableSet

data class RegisterStoreOperatingTime(
    val days: ImmutableSet<RegisterStoreDay>,
    val openingTime: String,
    val closingTime: String,
    val is24Hours: Boolean
)
