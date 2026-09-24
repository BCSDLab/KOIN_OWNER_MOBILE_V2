package `in`.koreatech.business.feature.store.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import `in`.koreatech.business.core.designsystem.component.button.primaryButtonColors
import `in`.koreatech.business.core.designsystem.component.progress.KoinProgressHeader
import `in`.koreatech.business.core.designsystem.component.progress.KoinProgressIndicator
import `in`.koreatech.business.core.designsystem.component.topbar.KoinTopAppBar
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_next
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_category_description
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_category_input
import `in`.koreatech.business.core.designsystem.generated.resources.register_store_category_step
import `in`.koreatech.business.core.designsystem.noRippleClickable
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import `in`.koreatech.business.feature.store.register.model.RegisterStoreCategory
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RegisterStoreCategoryScreen(
    title: String,
    categories: ImmutableList<RegisterStoreCategory>,
    selectedCategoryId: Int?,
    onCategorySelect: (Int) -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = KoinTheme.colors.neutral75,
        topBar = {
            KoinTopAppBar(
                modifier = Modifier.fillMaxWidth().background(KoinTheme.colors.neutral0),
                title = { Text(title, style = KoinTheme.typography.medium18) },
                onNavigationIconClick = onBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            KoinProgressHeader(stringResource(Res.string.register_store_category_step), 1, 4)
            Spacer(Modifier.height(8.dp))
            KoinProgressIndicator(1, 4)
            Spacer(Modifier.height(48.dp))
            Text(stringResource(Res.string.register_store_category_input), style = KoinTheme.typography.medium18)
            Spacer(Modifier.height(8.dp))
            Text(
                stringResource(Res.string.register_store_category_description),
                style = KoinTheme.typography.regular13,
                color = KoinTheme.colors.neutral500
            )
            Spacer(Modifier.height(24.dp))
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                categories.chunked(3).forEach { rowCategories ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        rowCategories.forEach { category ->
                            val selected = category.id == selectedCategoryId
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(if (selected) KoinTheme.colors.primary100 else KoinTheme.colors.neutral0)
                                    .noRippleClickable { onCategorySelect(category.id) }
                                    .padding(vertical = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(if (selected) KoinTheme.colors.primary500 else KoinTheme.colors.neutral100),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        category.name.take(1),
                                        style = KoinTheme.typography.bold15,
                                        color = if (selected) Color.White else KoinTheme.colors.neutral600
                                    )
                                }
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    category.name,
                                    style = KoinTheme.typography.medium14,
                                    color = if (selected) KoinTheme.colors.primary600 else KoinTheme.colors.neutral700
                                )
                            }
                        }
                        repeat(3 - rowCategories.size) { Spacer(Modifier.weight(1f)) }
                    }
                }
            }
            Spacer(Modifier.weight(1f))
            Spacer(Modifier.height(32.dp))
            Button(
                onClick = onNext,
                enabled = selectedCategoryId != null,
                modifier = Modifier.fillMaxWidth(),
                shape = KoinTheme.shapes.small,
                colors = primaryButtonColors(),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text(stringResource(Res.string.common_next), style = KoinTheme.typography.medium16)
            }
        }
    }
}
