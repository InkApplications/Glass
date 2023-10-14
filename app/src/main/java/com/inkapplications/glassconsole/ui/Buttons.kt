package com.inkapplications.glassconsole.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Simple Button display.
 */
@Composable
fun Button(
    text: String
) {
    Box(
        modifier = Modifier.border(1.dp, Color.Blue, RoundedCornerShape(4.dp))
    ) {
        BasicText(text = text)
    }
}
