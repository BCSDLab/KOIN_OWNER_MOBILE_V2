package `in`.koreatech.business.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.noRippleClickable
import `in`.koreatech.business.core.designsystem.theme.KoinTheme

data class KoinSelectableItem(
    val id: Int,
    val text: String
)

@Composable
fun KoinSelectableChipGroup(
    items: List<KoinSelectableItem>,
    selectedItemId: Int?,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            val selected = selectedItemId == item.id
            Text(
                text = item.text,
                style = KoinTheme.typography.medium14,
                color = if (selected) Color.White else KoinTheme.colors.neutral600,
                modifier =
                Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (selected) KoinTheme.colors.primary500 else Color.White)
                    .border(
                        width = 1.dp,
                        color = if (selected) KoinTheme.colors.primary500 else KoinTheme.colors.neutral300,
                        shape = RoundedCornerShape(20.dp)
                    ).noRippleClickable { onItemClick(item.id) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}
