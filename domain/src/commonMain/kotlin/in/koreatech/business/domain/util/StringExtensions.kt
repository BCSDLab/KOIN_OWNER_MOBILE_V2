package `in`.koreatech.business.domain.util

fun String.formatBusinessNumber(): String = filter(Char::isDigit).let { digits ->
    if (digits.length == 10) "${digits.take(3)}-${digits.substring(3, 5)}-${digits.drop(5)}" else digits
}

val String.isValidPhoneNumber: Boolean
    get() = matches(Regex("^010[0-9]{8}$"))
