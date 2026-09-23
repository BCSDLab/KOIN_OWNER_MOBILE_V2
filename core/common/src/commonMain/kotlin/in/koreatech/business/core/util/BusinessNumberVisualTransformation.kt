package `in`.koreatech.business.core.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class BusinessNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 10) text.text.substring(0..9) else text.text

        var out = ""
        for (i in trimmed.indices) {
            if (i == 3 || i == 5) out += "-"
            out += trimmed[i]
        }
        return TransformedText(AnnotatedString(out), businessNumberOffsetTranslator)
    }

    private val businessNumberOffsetTranslator = object : OffsetMapping {

        override fun originalToTransformed(offset: Int): Int =
            when (offset) {
                in 0..3 -> offset
                in 4..5 -> offset + 1
                else -> (offset + 2).coerceAtMost(12)
            }

        override fun transformedToOriginal(offset: Int): Int =
            when (offset) {
                in 0..2 -> offset
                in 3..4 -> offset - 1
                else -> offset - 2
            }
    }
}
