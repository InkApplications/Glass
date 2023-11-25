package com.inkapplications.glassconsole

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.inkapplications.glassconsole.structures.*
import com.inkapplications.glassconsole.ui.VerticalGridItemLayout
import com.inkapplications.glassconsole.ui.theme.InkTheme

/**
 * Handles the UI based on a specified configuration model.
 */
@Composable
fun DisplayScreen(
    config: DisplayConfig,
    connected: Boolean,
    onButtonClick: (ButtonItem) -> Unit,
) {
    Column {
        if (!connected) {
            BasicText(
                text = "No internet connection",
                style = InkTheme.typography.body.copy(
                    color = InkTheme.color.error,
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            Spacer(modifier = Modifier.padding(InkTheme.spacing.item))
        }

        when (val layout = config.layout) {
            is LayoutType.VerticalGrid -> VerticalGridItemLayout(layout, config.items, onButtonClick)
        }
    }
}
