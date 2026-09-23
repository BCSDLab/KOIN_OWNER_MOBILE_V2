package `in`.koreatech.business.feature.menu.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.component.KoinUnderlineTextField
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_delete
import `in`.koreatech.business.core.designsystem.generated.resources.common_won
import `in`.koreatech.business.core.designsystem.generated.resources.menu_category
import `in`.koreatech.business.core.designsystem.generated.resources.menu_option_format
import `in`.koreatech.business.core.designsystem.generated.resources.menu_option_name_hint
import `in`.koreatech.business.core.designsystem.generated.resources.menu_price
import `in`.koreatech.business.core.designsystem.noRippleClickable
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.core.util.CurrencyVisualTransformation
import `in`.koreatech.business.domain.model.store.OwnerMenuCategoryOption
import `in`.koreatech.business.feature.menu.form.EditableMenuPrice
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun MenuCategorySelector(
    categories: List<OwnerMenuCategoryOption>,
    selectedIds: Set<Int>,
    onCategoryClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(Res.string.menu_category),
            style = KoinTheme.typography.medium15,
            color = KoinTheme.colors.neutral800
        )
        categories.chunked(3).forEach { row ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { category ->
                    val selected = category.id in selectedIds
                    Text(
                        category.name,
                        style = KoinTheme.typography.medium13,
                        color = if (selected) Color.White else KoinTheme.colors.neutral600,
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                if (selected) KoinTheme.colors.primary500 else Color.White,
                                RoundedCornerShape(8.dp)
                            ).border(
                                1.dp,
                                if (selected) KoinTheme.colors.primary500 else KoinTheme.colors.neutral300,
                                RoundedCornerShape(8.dp)
                            ).noRippleClickable { onCategoryClick(category.id) }
                            .padding(vertical = 10.dp, horizontal = 8.dp)
                    )
                }
                repeat(3 - row.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
internal fun MenuOptionPriceRow(
    index: Int,
    value: EditableMenuPrice,
    onOptionChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(KoinTheme.colors.neutral50, RoundedCornerShape(12.dp))
            .border(1.dp, KoinTheme.colors.neutral200, RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                stringResource(Res.string.menu_option_format, index + 1),
                style = KoinTheme.typography.medium14,
                color = KoinTheme.colors.neutral700,
                modifier = Modifier.weight(1f)
            )
            Text(
                stringResource(Res.string.common_delete),
                style = KoinTheme.typography.medium13,
                color = KoinTheme.colors.danger600,
                modifier = Modifier
                    .noRippleClickable(onClick = onDelete)
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            KoinUnderlineTextField(
                value = value.option,
                onValueChange = onOptionChange,
                placeholder = stringResource(Res.string.menu_option_name_hint),
                modifier = Modifier.weight(1f)
            )
            KoinUnderlineTextField(
                value = value.price,
                onValueChange = onPriceChange,
                placeholder = stringResource(Res.string.menu_price),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = CurrencyVisualTransformation(),
                suffix = {
                    Text(
                        text = stringResource(Res.string.common_won),
                        style = KoinTheme.typography.regular14,
                        color = KoinTheme.colors.neutral600
                    )
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}
