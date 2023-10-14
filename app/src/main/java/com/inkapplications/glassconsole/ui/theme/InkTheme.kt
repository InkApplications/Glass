package com.inkapplications.glassconsole.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Application theme configurations for color/spacing/typography.
 */
object InkTheme {
    private object Palette {
        val Black = Color(0xFF000000)
        val White = Color(0xFFFFFFFF)
        val TransluscentWhite = Color(0x80FFFFFF)
        val Dark = Color(0xFF212121)
        val TransluscentDark = Color(0x80212121)
        val Light = Color(0xFFF8F8F8)
        val Brand = Color(0xFF008DA9)
        val Red = Color(0xFFf92772)
        val Orange = Color(0xFFfe9720)
        val Green = Color(0xFF00a600)
        val Blue = Color(0xFF3495CC)
        val Magenta = Color(0xFF9e6efe)

        val ThemeAccent
            @Composable
            @ReadOnlyComposable get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                colorResource(android.R.color.system_accent1_400)
            } else {
                Brand
            }
    }

    val spacing
        @Composable
        @ReadOnlyComposable
        get() = SpacingVariant(
            gutter = 16.dp,
            content = 8.dp,
            item = 8.dp,
            icon = 8.dp,
            clickSafety = 16.dp,
        )

    val color
        @Composable
        @ReadOnlyComposable
        get() = if (isSystemInDarkTheme()) {
            ColorVariant(
                foreground = Palette.White,
                background = Palette.Black,
                surface = Palette.Dark,
                accent = Palette.Brand,
                accentForeground = Palette.White,
                warning = Palette.Orange,
                error = Palette.Red,
                success = Palette.Green,
            )
        } else {
            ColorVariant(
                foreground = Palette.Dark,
                background = Palette.White,
                surface = Palette.Light,
                accent = Palette.Brand,
                accentForeground = Palette.White,
                warning = Palette.Orange,
                error = Palette.Red,
                success = Palette.Green,
            )
        }

    val typography
        @Composable
        @ReadOnlyComposable
        get() = TypographyVariant(
            h1 = TextStyle(
                fontFamily = InkFonts.titleFont,
                fontWeight = FontWeight.Normal,
                fontSize = 34.sp,
                letterSpacing = 0.25.sp,
                color = color.foreground,
            ),
            h2 = TextStyle(
                fontFamily = InkFonts.titleFont,
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp,
                letterSpacing = 0.sp,
                color = color.foreground,
            ),
            h3 = TextStyle(
                fontFamily = InkFonts.titleFont,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                letterSpacing = 0.15.sp,
                color = color.foreground,
            ),
            body = TextStyle(
                fontFamily = InkFonts.contentFont,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                letterSpacing = 0.5.sp,
                color = color.foreground,
            ),
            caption = TextStyle(
                fontFamily = InkFonts.contentFont,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                letterSpacing = 0.4.sp,
                color = color.foreground,
            ),
        )
}
