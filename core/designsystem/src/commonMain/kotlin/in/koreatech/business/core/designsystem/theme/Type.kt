package `in`.koreatech.business.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.designsystem.generated.resources.pretendard_bold
import `in`.koreatech.business.core.designsystem.generated.resources.pretendard_medium
import `in`.koreatech.business.core.designsystem.generated.resources.pretendard_regular
import org.jetbrains.compose.resources.Font

@Immutable
data class KoinTypography(
    val regular10: TextStyle,
    val regular12: TextStyle,
    val regular13: TextStyle,
    val regular14: TextStyle,
    val regular15: TextStyle,
    val regular16: TextStyle,
    val regular18: TextStyle,
    val medium12: TextStyle,
    val medium13: TextStyle,
    val medium14: TextStyle,
    val medium15: TextStyle,
    val medium16: TextStyle,
    val medium18: TextStyle,
    val bold12: TextStyle,
    val bold13: TextStyle,
    val bold14: TextStyle,
    val bold15: TextStyle,
    val bold16: TextStyle,
    val bold18: TextStyle,
    val bold20: TextStyle
)

internal val DefaultTextStyle: TextStyle = TextStyle(
    fontStyle = FontStyle.Normal,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None
    ),
    letterSpacing = 0.sp
)

internal val RegularStyle1 = DefaultTextStyle.copy(
    fontSize = 10.sp,
    fontWeight = FontWeight.Normal,
    lineHeight = 16.sp
)
internal val RegularStyle2 = DefaultTextStyle.copy(
    fontSize = 12.sp,
    fontWeight = FontWeight.Normal,
    lineHeight = 19.2.sp
)
internal val RegularStyle3 = DefaultTextStyle.copy(
    fontSize = 13.sp,
    fontWeight = FontWeight.Normal,
    lineHeight = 20.8.sp
)
internal val RegularStyle4 = DefaultTextStyle.copy(
    fontSize = 14.sp,
    fontWeight = FontWeight.Normal,
    lineHeight = 22.4.sp
)
internal val RegularStyle5 = DefaultTextStyle.copy(
    fontSize = 15.sp,
    fontWeight = FontWeight.Normal,
    lineHeight = 24.sp
)
internal val RegularStyle6 = DefaultTextStyle.copy(
    fontSize = 16.sp,
    fontWeight = FontWeight.Normal,
    lineHeight = 25.6.sp
)
internal val RegularStyle7 = DefaultTextStyle.copy(
    fontSize = 18.sp,
    fontWeight = FontWeight.Normal,
    lineHeight = 28.8.sp
)

internal val MediumStyle1 = DefaultTextStyle.copy(
    fontSize = 12.sp,
    fontWeight = FontWeight.Medium,
    lineHeight = 19.2.sp
)
internal val MediumStyle2 = DefaultTextStyle.copy(
    fontSize = 13.sp,
    fontWeight = FontWeight.Medium,
    lineHeight = 20.8.sp
)
internal val MediumStyle3 = DefaultTextStyle.copy(
    fontSize = 14.sp,
    fontWeight = FontWeight.Medium,
    lineHeight = 22.4.sp
)
internal val MediumStyle4 = DefaultTextStyle.copy(
    fontSize = 15.sp,
    fontWeight = FontWeight.Medium,
    lineHeight = 24.sp
)
internal val MediumStyle5 = DefaultTextStyle.copy(
    fontSize = 16.sp,
    fontWeight = FontWeight.Medium,
    lineHeight = 25.6.sp
)
internal val MediumStyle6 = DefaultTextStyle.copy(
    fontSize = 18.sp,
    fontWeight = FontWeight.Medium,
    lineHeight = 28.8.sp
)

internal val BoldStyle1 = DefaultTextStyle.copy(
    fontSize = 12.sp,
    fontWeight = FontWeight.Bold,
    lineHeight = 19.2.sp
)
internal val BoldStyle2 = DefaultTextStyle.copy(
    fontSize = 13.sp,
    fontWeight = FontWeight.Bold,
    lineHeight = 20.8.sp
)
internal val BoldStyle3 = DefaultTextStyle.copy(
    fontSize = 14.sp,
    fontWeight = FontWeight.Bold,
    lineHeight = 22.4.sp
)
internal val BoldStyle4 = DefaultTextStyle.copy(
    fontSize = 15.sp,
    fontWeight = FontWeight.Bold,
    lineHeight = 24.sp
)
internal val BoldStyle5 = DefaultTextStyle.copy(
    fontSize = 16.sp,
    fontWeight = FontWeight.Bold,
    lineHeight = 25.6.sp
)
internal val BoldStyle6 = DefaultTextStyle.copy(
    fontSize = 18.sp,
    fontWeight = FontWeight.Bold,
    lineHeight = 28.8.sp
)
internal val BoldStyle7 = DefaultTextStyle.copy(
    fontSize = 20.sp,
    fontWeight = FontWeight.Bold,
    lineHeight = 30.sp
)

@Composable
internal fun createKoinTypography(): KoinTypography {
    val pretendard = FontFamily(
        Font(Res.font.pretendard_bold, FontWeight.Bold, FontStyle.Normal),
        Font(Res.font.pretendard_bold, FontWeight.W600, FontStyle.Normal),
        Font(Res.font.pretendard_medium, FontWeight.Medium, FontStyle.Normal),
        Font(Res.font.pretendard_regular, FontWeight.Normal, FontStyle.Normal)
    )

    fun TextStyle.pretendard(): TextStyle = copy(fontFamily = pretendard)

    return KoinTypography(
        regular10 = RegularStyle1.pretendard(),
        regular12 = RegularStyle2.pretendard(),
        regular13 = RegularStyle3.pretendard(),
        regular14 = RegularStyle4.pretendard(),
        regular15 = RegularStyle5.pretendard(),
        regular16 = RegularStyle6.pretendard(),
        regular18 = RegularStyle7.pretendard(),
        medium12 = MediumStyle1.pretendard(),
        medium13 = MediumStyle2.pretendard(),
        medium14 = MediumStyle3.pretendard(),
        medium15 = MediumStyle4.pretendard(),
        medium16 = MediumStyle5.pretendard(),
        medium18 = MediumStyle6.pretendard(),
        bold12 = BoldStyle1.pretendard(),
        bold13 = BoldStyle2.pretendard(),
        bold14 = BoldStyle3.pretendard(),
        bold15 = BoldStyle4.pretendard(),
        bold16 = BoldStyle5.pretendard(),
        bold18 = BoldStyle6.pretendard(),
        bold20 = BoldStyle7.pretendard()
    )
}
