package com.inkapplications.glassconsole.ui

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import com.inkapplications.glassconsole.structures.ButtonItem
import com.inkapplications.glassconsole.structures.DisplayItem
import com.inkapplications.glassconsole.structures.StatusItem
import com.inkapplications.glassconsole.structures.TextItem
import com.inkapplications.glassconsole.ui.theme.InkTheme

/**
 * Render a Display Item by type.
 */
@Composable
fun Item(
    item: DisplayItem,
    onButtonClick: (ButtonItem) -> Unit,
) {
    when (item) {
        is ButtonItem -> Button(
            text = item.text,
            onClick = { onButtonClick(item) },
            indicator = item.indicator,
            latching = item.latching,
        )
        is TextItem.H1 -> BasicText(text = item.text, style = InkTheme.typography.h1)
        is TextItem.H2 -> BasicText(text = item.text, style = InkTheme.typography.h2)
        is TextItem.H3 -> BasicText(text = item.text, style = InkTheme.typography.h3)
        is TextItem.Body -> BasicText(text = item.text, style = InkTheme.typography.body)
        is StatusItem -> com.inkapplications.glassconsole.ui.Status(text = item.text, indicator = item.indicator)
    }
}
