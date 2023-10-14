package com.inkapplications.glassconsole.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Color configuration schema available for UI elements.
 */
data class ColorVariant(
    val foreground: Color,
    val background: Color,
    val surface: Color,
    val accent: Color,
    val accentForeground: Color,
    val warning: Color,
    val error: Color,
    val success: Color,
) {
    val dimForeground: Color = foreground.copy(alpha = 0.4f)
}
