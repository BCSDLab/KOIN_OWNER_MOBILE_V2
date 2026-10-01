package `in`.koreatech.business.core.util

import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer

class BusinessNumberOutputTransformation : OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
        if (length > 5) {
            replace(5, 5, "-")
        }
        if (length > 3) {
            replace(3, 3, "-")
        }
    }
}
