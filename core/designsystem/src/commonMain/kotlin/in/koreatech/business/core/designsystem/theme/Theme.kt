package `in`.koreatech.business.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

internal val LocalKoinColorPalette = staticCompositionLocalOf {
    KoinLightColorPalette
}

internal val LocalKoinTypography = staticCompositionLocalOf {
    KoinTypography(
        regular10 = RegularStyle1,
        regular12 = RegularStyle2,
        regular13 = RegularStyle3,
        regular14 = RegularStyle4,
        regular15 = RegularStyle5,
        regular16 = RegularStyle6,
        regular18 = RegularStyle7,
        medium12 = MediumStyle1,
        medium13 = MediumStyle2,
        medium14 = MediumStyle3,
        medium15 = MediumStyle4,
        medium16 = MediumStyle5,
        medium18 = MediumStyle6,
        bold12 = BoldStyle1,
        bold13 = BoldStyle2,
        bold14 = BoldStyle3,
        bold15 = BoldStyle4,
        bold16 = BoldStyle5,
        bold18 = BoldStyle6,
        bold20 = BoldStyle7
    )
}

internal val KoinLightColorScheme = lightColorScheme(
    primary = purple50
)

internal val KoinDarkColorScheme = lightColorScheme(
    primary = purple50
)

internal val LocalShapes = staticCompositionLocalOf {
    Shapes
}

@Composable
expect fun StatusBar(darkTheme: Boolean)

@Composable
fun KoinTheme(
    darkTheme: Boolean = false, // TODO: Change to isSystemInDarkTheme() after dark theme ready
    content: @Composable () -> Unit
) {
    val extendedColors = if (darkTheme) {
        KoinLightColorPalette
    } else {
        KoinDarkColorPalette
    }

    val colorScheme = when {
        darkTheme -> KoinDarkColorScheme
        else -> KoinLightColorScheme
    }

    StatusBar(darkTheme)

    val koinTypography = createKoinTypography()

    val typography = Typography(
        displayLarge = koinTypography.medium18,
        displayMedium = koinTypography.medium16,
        displaySmall = koinTypography.medium15,
        headlineLarge = koinTypography.medium18,
        headlineMedium = koinTypography.medium16,
        headlineSmall = koinTypography.medium15,
        titleLarge = koinTypography.medium16,
        titleMedium = koinTypography.medium15,
        titleSmall = koinTypography.medium14,
        bodyLarge = koinTypography.regular15,
        bodyMedium = koinTypography.regular14,
        bodySmall = koinTypography.regular13,
        labelLarge = koinTypography.regular13,
        labelMedium = koinTypography.regular12,
        labelSmall = koinTypography.regular10
    )

    val shapes = Shapes

    CompositionLocalProvider(
        LocalKoinColorPalette provides extendedColors,
        LocalKoinTypography provides koinTypography,
        LocalShapes provides shapes
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            shapes = Shapes,
            content = content
        )
    }
}

object KoinTheme {
    val colors: KoinColorPalette
        @Composable
        @ReadOnlyComposable
        get() = LocalKoinColorPalette.current
    val typography: KoinTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalKoinTypography.current
    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current
}
