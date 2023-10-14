package com.inkapplications.glassconsole.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.inkapplications.glassconsole.structures.Indicator
import com.inkapplications.glassconsole.ui.theme.InkTheme

/**
 * Simple Button display.
 */
@Composable
fun Button(
    text: String,
    indicator: Indicator = Indicator.Nominal,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .semantics { role = Role.Button }
            .border(1.dp, indicator.color, RoundedCornerShape(4.dp))
            .clickable(onClick = onClick)
            .padding(InkTheme.spacing.clickSafety)
    ) {
        BasicText(text = text, style = InkTheme.typography.body)
    }
}
