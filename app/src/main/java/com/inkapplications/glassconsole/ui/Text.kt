package com.inkapplications.glassconsole.ui

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

/**
 * Large Header text.
 */
@Composable
fun H1(text: String) {
    BasicText(text = text, style = TextStyle.Default.copy(fontSize = 24.sp))
}
