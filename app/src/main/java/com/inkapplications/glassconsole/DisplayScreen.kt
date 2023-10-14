package com.inkapplications.glassconsole

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.inkapplications.glassconsole.structures.DisplayConfig
import com.inkapplications.glassconsole.structures.DisplayItem
import com.inkapplications.glassconsole.ui.Button
import com.inkapplications.glassconsole.ui.H1

/**
 * Handles the UI based on a specified configuration model.
 */
@Composable
fun DisplayScreen(config: DisplayConfig) {
    Column {
        config.items.forEach { item ->
            when (item) {
                is DisplayItem.Button -> Button(text = item.text)
                is DisplayItem.H1 -> H1(text = item.text)
            }
        }
    }
}

