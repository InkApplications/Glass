package com.inkapplications.glassconsole.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.inkapplications.glassconsole.structures.Indicator
import com.inkapplications.glassconsole.ui.theme.InkTheme
import kotlinx.coroutines.delay

/**
 * Simple Button display.
 */
@Composable
fun Button(
    text: String,
    indicator: Indicator = Indicator.Nominal,
    latching: Boolean = false,
    onClick: () -> Unit,
) {
    var latched by remember(text, indicator) { mutableStateOf(false) }
    val borderColor = if (latched) InkTheme.color.dimForeground else indicator.color
    val latchingClick = {
        if (latching) {
            latched = true
        }
        onClick()
    }
    Box(
        modifier = Modifier
            .semantics { role = Role.Button }
            .border(1.dp, borderColor, RoundedCornerShape(4.dp))
            .let { if (latched) it else it.clickable(onClick = latchingClick) }
            .padding(InkTheme.spacing.clickSafety)
    ) {
        if (latched) {
            LaunchedEffect(latched) {
                delay(10_000)
                latched = false
            }
            BasicText(text = "-".repeat(text.length), style = InkTheme.typography.body)

        } else {
            BasicText(text = text, style = InkTheme.typography.body)
        }
    }
}
