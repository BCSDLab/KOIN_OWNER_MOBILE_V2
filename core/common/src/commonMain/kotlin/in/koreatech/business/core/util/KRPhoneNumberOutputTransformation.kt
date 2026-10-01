package `in`.koreatech.business.core.util

import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer

class KRPhoneNumberOutputTransformation : OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
        val secondSeparatorIndex = if (length == 11) 7 else 6
        if (length > secondSeparatorIndex) {
            replace(secondSeparatorIndex, secondSeparatorIndex, "-")
        }
        if (length > 3) {
            replace(3, 3, "-")
        }
    }
}
