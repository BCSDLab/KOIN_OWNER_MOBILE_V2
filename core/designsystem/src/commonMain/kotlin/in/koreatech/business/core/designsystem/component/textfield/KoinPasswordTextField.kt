package `in`.koreatech.business.core.designsystem.component.textfield

import androidx.compose.foundation.Image
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import `in`.koreatech.business.core.designsystem.component.KoinUnderlineTextField
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.ic_password_hidden
import `in`.koreatech.business.core.designsystem.generated.resources.ic_password_visible
import `in`.koreatech.business.core.designsystem.noRippleClickable
import org.jetbrains.compose.resources.vectorResource

@Composable
fun KoinPasswordTextField(
    state: TextFieldState,
    hint: String,
    modifier: Modifier = Modifier,
    maxLength: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val currentPasswordVisible by rememberUpdatedState(passwordVisible)
    val outputTransformation = remember {
        OutputTransformation {
            if (!currentPasswordVisible) {
                for (index in length - 1 downTo 0) {
                    replace(index, index + 1, PASSWORD_MASK.toString())
                }
            }
        }
    }
    KoinUnderlineTextField(
        state = state,
        modifier = modifier,
        hint = hint,
        maxLength = maxLength,
        keyboardOptions = keyboardOptions,
        outputTransformation = outputTransformation,
        suffix = {
            Image(
                imageVector = vectorResource(if (passwordVisible) Res.drawable.ic_password_visible else Res.drawable.ic_password_hidden),
                contentDescription = null,
                modifier = Modifier.noRippleClickable { passwordVisible = !passwordVisible }
            )
        }
    )
}

private const val PASSWORD_MASK = '●'
