package com.inkapplications.glassconsole.ui

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import com.inkapplications.glassconsole.ui.theme.InkTheme

/**
 * Large Header text.
 */
@Composable
fun H1(text: String) {
    BasicText(text = text, style = InkTheme.typography.h1)
}
