package `in`.koreatech.business.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.common_image_placeholder
import `in`.koreatech.business.core.designsystem.theme.KoinTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun KoinImageThumbnail(
    imageUrl: String?,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Crop,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(KoinTheme.colors.neutral100),
        contentAlignment = Alignment.Center
    ) {
        if (imageUrl == null) {
            KoinImageThumbnailPlaceholder()
        } else {
            SubcomposeAsyncImage(
                model = imageUrl,
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = Modifier.fillMaxSize(),
                loading = { Box(modifier = Modifier.fillMaxSize().skeleton(shape = RoundedCornerShape(8.dp))) },
                error = { KoinImageThumbnailPlaceholder() }
            )
        }
    }
}

@Composable
private fun KoinImageThumbnailPlaceholder() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(Res.string.common_image_placeholder),
            style = KoinTheme.typography.regular12,
            color = KoinTheme.colors.neutral500
        )
    }
}
