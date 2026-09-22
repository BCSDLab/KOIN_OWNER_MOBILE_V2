package `in`.koreatech.business.feature.home.util

import `in`.koreatech.business.domain.model.store.OperatingTime

internal fun OperatingTime.toDisplayText(
    dayNames: Map<String, String>,
    closedText: String
): String {
    val day = dayNames[dayOfWeek] ?: dayOfWeek
    return if (isClosed) {
        "$day $closedText"
    } else {
        "$day ${openTime.orEmpty()}–${closeTime.orEmpty()}"
    }
}
