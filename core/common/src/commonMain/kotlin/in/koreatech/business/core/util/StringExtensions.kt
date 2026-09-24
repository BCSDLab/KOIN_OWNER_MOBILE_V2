package `in`.koreatech.business.core.util

fun String.toKRPhoneNumber(): String {
    val digits = filter(Char::isDigit).take(11)
    val middleEnd = if (digits.length == 11) 7 else 6
    return buildString {
        digits.forEachIndexed { index, character ->
            if (index == 3 || index == middleEnd) append('-')
            append(character)
        }
    }
}
