package `in`.koreatech.business.core.file

internal fun String.toImageContentType(): String = when (substringAfterLast('.', "").lowercase()) {
    "png" -> "image/png"
    "webp" -> "image/webp"
    else -> "image/jpeg"
}
