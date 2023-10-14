package com.inkapplications.glassconsole

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.inkapplications.glassconsole.structures.DisplayConfig
import com.inkapplications.glassconsole.structures.DisplayItem
import com.inkapplications.glassconsole.ui.Button
import com.inkapplications.glassconsole.ui.H1
import com.inkapplications.glassconsole.ui.theme.InkTheme

/**
 * Handles the UI based on a specified configuration model.
 */
@Composable
fun DisplayScreen(config: DisplayConfig) {
    Column(
        modifier = Modifier.padding(InkTheme.spacing.gutter),
    ) {
        config.items.forEach { item ->
            when (item) {
                is DisplayItem.Button -> Button(text = item.text)
                is DisplayItem.H1 -> H1(text = item.text)
            }
        }
    }
}

