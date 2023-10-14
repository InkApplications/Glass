package com.inkapplications.glassconsole

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

/**
 * Used when the display is first booted and has no configuration data.
 */
@Composable
fun EmptyScreen() {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        BasicText(
            text = "No data",
        )
    }
}
