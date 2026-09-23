package `in`.koreatech.business.core.designsystem.component.textfield

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import `in`.koreatech.business.core.designsystem.component.KoinUnderlineTextField
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.ic_password_hidden
import `in`.koreatech.business.core.designsystem.generated.resources.ic_password_visible
import `in`.koreatech.business.core.designsystem.noRippleClickable
import org.jetbrains.compose.resources.vectorResource

@Composable
fun KoinPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
) {
    var passwordVisible by remember { mutableStateOf(false) }

    KoinUnderlineTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        hint = hint,
        keyboardOptions = keyboardOptions,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(mask = '●'),
        suffix = {
            Image(
                imageVector = vectorResource(if (passwordVisible) Res.drawable.ic_password_visible else Res.drawable.ic_password_hidden),
                contentDescription = null,
                modifier = Modifier.noRippleClickable { passwordVisible = !passwordVisible }
            )
        }
    )
}
