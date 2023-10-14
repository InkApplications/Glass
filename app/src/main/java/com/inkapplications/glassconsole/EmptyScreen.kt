package com.inkapplications.glassconsole

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.inkapplications.glassconsole.ui.theme.InkTheme

/**
 * Used when the display is first booted and has no configuration data.
 */
@Composable
fun EmptyScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(InkTheme.spacing.gutter),
    ) {
        BasicText(
            text = "No data",
            style = InkTheme.typography.body,
        )
    }
}
