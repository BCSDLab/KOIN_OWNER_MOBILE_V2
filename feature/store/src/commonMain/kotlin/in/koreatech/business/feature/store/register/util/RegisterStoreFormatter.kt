package `in`.koreatech.business.feature.store.register.util

internal fun String.isValidTimeInput(): Boolean {
    if (length != TIME_INPUT_LENGTH) return false
    val hour = take(HOUR_LENGTH).toIntOrNull() ?: return false
    val minute = takeLast(MINUTE_LENGTH).toIntOrNull() ?: return false
    return hour in 0..23 && minute in 0..59
}

internal fun String.toTimeText(): String =
    if (length == TIME_INPUT_LENGTH) {
        "${take(HOUR_LENGTH)}:${takeLast(MINUTE_LENGTH)}"
    } else {
        this
    }

private const val TIME_INPUT_LENGTH = 4
private const val HOUR_LENGTH = 2
private const val MINUTE_LENGTH = 2
