package `in`.koreatech.business.feature.signin.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.theme.KoinTheme

@Composable
fun SignInTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = if (isPassword) PasswordVisualTransformation(mask = '●') else VisualTransformation.None,
        textStyle = KoinTheme.typography.regular14,
        decorationBox = { innerTextField ->
            Column {
                Row(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.weight(1f)) {
                        if (value.isEmpty()) Text(hint, color = KoinTheme.colors.neutral400, style = KoinTheme.typography.regular14)
                        innerTextField()
                    }
                    if (value.isNotEmpty()) {
                        Text(
                            if (isPassword) "◉" else "×",
                            color = KoinTheme.colors.neutral600,
                            style = KoinTheme.typography.regular16,
                            modifier =
                            Modifier
                                .clickable {
                                    onValueChange("")
                                }
                        )
                    }
                }
                HorizontalDivider(Modifier.fillMaxWidth(), color = KoinTheme.colors.neutral400)
            }
        },
        modifier = modifier
    )
}
