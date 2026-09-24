package `in`.koreatech.business.core.util

fun Int.toCurrencyText(): String = toString().reversed().chunked(3).joinToString(",").reversed()
