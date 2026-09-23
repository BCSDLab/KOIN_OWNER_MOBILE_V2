package `in`.koreatech.business.core.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CurrencyVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val value = text.text
        val formatted = value.reversed().chunked(3).joinToString(",").reversed()
        val mapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 0) return 0
                var digitCount = 0
                formatted.forEachIndexed { index, character ->
                    if (character.isDigit()) digitCount++
                    if (digitCount == offset) return index + 1
                }
                return formatted.length
            }

            override fun transformedToOriginal(offset: Int): Int = formatted.take(offset.coerceAtMost(formatted.length)).count(Char::isDigit)
        }
        return TransformedText(AnnotatedString(formatted), mapping)
    }
}

fun Int.toCurrencyText(): String = toString().reversed().chunked(3).joinToString(",").reversed()
