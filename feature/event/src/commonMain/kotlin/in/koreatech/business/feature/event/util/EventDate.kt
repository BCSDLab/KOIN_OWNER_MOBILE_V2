package `in`.koreatech.business.feature.event.util

private const val MILLIS_PER_DAY = 86_400_000L

internal fun dateToEpochMillis(date: String): Long? {
    val parts = date.split('-')
    if (parts.size != 3) return null
    var year = parts[0].toIntOrNull() ?: return null
    val month = parts[1].toIntOrNull() ?: return null
    val day = parts[2].toIntOrNull() ?: return null
    if (month !in 1..12 || day !in 1..daysInMonth(year, month)) return null
    year -= if (month <= 2) 1 else 0
    val era = year / 400
    val yearOfEra = year - era * 400
    val adjustedMonth = month + if (month > 2) -3 else 9
    val dayOfYear = (153 * adjustedMonth + 2) / 5 + day - 1
    val dayOfEra = yearOfEra * 365 + yearOfEra / 4 - yearOfEra / 100 + dayOfYear
    return (era * 146_097L + dayOfEra - 719_468L) * MILLIS_PER_DAY
}

private fun daysInMonth(
    year: Int,
    month: Int
): Int = when (month) {
    2 -> if (year.isLeapYear()) 29 else 28
    4, 6, 9, 11 -> 30
    else -> 31
}

private fun Int.isLeapYear(): Boolean = this % 4 == 0 && (this % 100 != 0 || this % 400 == 0)

internal fun epochMillisToDate(epochMillis: Long): String {
    var days = epochMillis / MILLIS_PER_DAY + 719_468L
    val era = days / 146_097L
    val dayOfEra = days - era * 146_097L
    val yearOfEra = (
        dayOfEra - dayOfEra / 1_460L + dayOfEra / 36_524L - dayOfEra / 146_096L
        ) / 365L
    var year = yearOfEra + era * 400L
    val dayOfYear = dayOfEra - (365L * yearOfEra + yearOfEra / 4L - yearOfEra / 100L)
    val monthPrime = (5L * dayOfYear + 2L) / 153L
    val day = dayOfYear - (153L * monthPrime + 2L) / 5L + 1L
    val month = monthPrime + if (monthPrime < 10L) 3L else -9L
    year += if (month <= 2L) 1L else 0L
    return "${year.toString().padStart(4, '0')}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}"
}
