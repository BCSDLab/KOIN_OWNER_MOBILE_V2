package `in`.koreatech.business.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.theme.KoinTheme

@Composable
fun KoinUnderlineTextField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    suffix: String? = null
) {
    Column(modifier = modifier) {
        if (title.isNotEmpty()) {
            Text(title, style = KoinTheme.typography.medium15, color = KoinTheme.colors.neutral800)
            Spacer(Modifier.height(8.dp))
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle =
            KoinTheme.typography.regular14.copy(
                color = KoinTheme.colors.neutral800,
                lineHeightStyle = null
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = singleLine,
            maxLines = if (singleLine) 1 else 4,
            decorationBox = { innerTextField ->
                Column(modifier = Modifier.width(IntrinsicSize.Min)) {
                    Row(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .then(if (singleLine) Modifier.height(44.dp) else Modifier.heightIn(min = 96.dp))
                            .padding(horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            if (value.isEmpty()) {
                                Text(
                                    placeholder,
                                    style = KoinTheme.typography.regular14.copy(lineHeightStyle = null),
                                    color = KoinTheme.colors.neutral400
                                )
                            }
                            innerTextField()
                        }
                        suffix?.let {
                            Text(it, style = KoinTheme.typography.regular14, color = KoinTheme.colors.neutral600)
                        }
                    }
                    HorizontalDivider(color = KoinTheme.colors.neutral400)
                }
            }
        )
    }
}
