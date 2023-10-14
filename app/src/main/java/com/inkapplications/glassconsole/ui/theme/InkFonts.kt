package com.inkapplications.glassconsole.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.inkapplications.glassconsole.R

/**
 * Fonts configured in the application.
 */
object InkFonts {
    /**
     * Font that should be used for general content elements like paragraphs.
     */
    val contentFont = FontFamily(
        Font(R.font.roboto_mono_light, FontWeight.Normal),
        Font(R.font.roboto_mono_medium, FontWeight.Bold),
    )

    /**
     * Font that should be used for display elements like titles.
     */
    val titleFont = contentFont
}
