package `in`.koreatech.business.core.util

import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer

class CurrencyOutputTransformation : OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
        var separatorIndex = length - 3
        while (separatorIndex > 0) {
            replace(separatorIndex, separatorIndex, ",")
            separatorIndex -= 3
        }
    }
}
