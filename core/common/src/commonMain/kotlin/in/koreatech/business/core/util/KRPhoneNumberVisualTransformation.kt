package `in`.koreatech.business.core.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class KRPhoneNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 11) text.text.substring(0..10) else text.text
        val isElevenDigitNumber = trimmed.length == 11

        var out = ""
        for (i in trimmed.indices) {
            if (i == 3 || (isElevenDigitNumber && i == 7) || (!isElevenDigitNumber && i == 6)) {
                out += "-"
            }
            out += trimmed[i]
        }
        return TransformedText(AnnotatedString(out), phoneNumberOffsetTranslator(isElevenDigitNumber))
    }

    private fun phoneNumberOffsetTranslator(isElevenDigitNumber: Boolean): OffsetMapping {
        val firstGroupEnd = if (isElevenDigitNumber) 7 else 6
        val secondGroupStart = if (isElevenDigitNumber) 7 else 6
        val secondGroupEnd = if (isElevenDigitNumber) 12 else 11
        val transformedLength = if (isElevenDigitNumber) 13 else 12
        val originalLength = if (isElevenDigitNumber) 11 else 10

        return object : OffsetMapping {

            override fun originalToTransformed(offset: Int): Int =
                when (offset) {
                    in 0..3 -> offset
                    in 4..firstGroupEnd -> offset + 1
                    else -> (offset + 2).coerceAtMost(transformedLength)
                }

            override fun transformedToOriginal(offset: Int): Int =
                when (offset) {
                    in 0..2 -> offset
                    in 3..(firstGroupEnd - 1) -> offset - 1
                    in secondGroupStart..secondGroupEnd -> offset - 2
                    else -> originalLength
                }
        }
    }
}
