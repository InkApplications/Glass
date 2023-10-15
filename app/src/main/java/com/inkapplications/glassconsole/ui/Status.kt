package com.inkapplications.glassconsole.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.inkapplications.glassconsole.structures.Indicator
import com.inkapplications.glassconsole.ui.theme.InkTheme

/**
 * A label with a dot next to it as a signifier.
 */
@Composable
fun Status(
    text: String,
    indicator: Indicator,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val color = indicator.color
        Canvas(
            modifier = Modifier.size(16.dp),
            onDraw = {
                drawCircle(
                    color = color,
                    radius = 8.dp.toPx(),
                )
            }
        )
        Spacer(modifier = Modifier.padding(4.dp))
        BasicText(text = text, style = InkTheme.typography.body)
    }
}
