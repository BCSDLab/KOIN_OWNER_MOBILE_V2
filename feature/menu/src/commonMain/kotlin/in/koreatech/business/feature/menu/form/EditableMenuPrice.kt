package `in`.koreatech.business.feature.menu.form

import androidx.compose.foundation.text.input.TextFieldState

data class EditableMenuPrice(
    val optionTextFieldState: TextFieldState = TextFieldState(),
    val priceTextFieldState: TextFieldState = TextFieldState()
) {
    val option: String get() = optionTextFieldState.text.toString()
    val price: String get() = priceTextFieldState.text.toString()
}
