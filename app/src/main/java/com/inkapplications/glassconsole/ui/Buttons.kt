package com.inkapplications.glassconsole.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.inkapplications.glassconsole.ui.theme.InkTheme

/**
 * Simple Button display.
 */
@Composable
fun Button(
    text: String
) {
    Box(
        modifier = Modifier
            .border(1.dp, InkTheme.color.accent, RoundedCornerShape(4.dp))
            .padding(InkTheme.spacing.clickSafety)
    ) {
        BasicText(text = text, style = InkTheme.typography.body)
    }
}
