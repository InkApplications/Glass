package com.inkapplications.glassconsole

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.inkapplications.glassconsole.structures.*
import com.inkapplications.glassconsole.ui.Button
import com.inkapplications.glassconsole.ui.theme.InkTheme

/**
 * Handles the UI based on a specified configuration model.
 */
@Composable
fun DisplayScreen(
    config: DisplayConfig,
    onButtonClick: (Button) -> Unit,
) {
    Column(
        modifier = Modifier.padding(InkTheme.spacing.gutter),
    ) {
        config.items.forEach { item ->
            when (item) {
                is Button -> Button(text = item.text, onClick = { onButtonClick(item) }, indicator = item.indicatorColor)
                is H1 -> BasicText(text = item.text, style = InkTheme.typography.h1)
                is H2 -> BasicText(text = item.text, style = InkTheme.typography.h2)
                is H3 -> BasicText(text = item.text, style = InkTheme.typography.h3)
                is Body -> BasicText(text = item.text, style = InkTheme.typography.body)
            }
            Spacer(modifier = Modifier.padding(InkTheme.spacing.item))
        }
    }
}
