package com.inkapplications.glassconsole

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.inkapplications.glassconsole.ui.theme.InkTheme

/**
 * Used when the display is first loading and has no data at all.
 */
@Composable
fun InitialScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(InkTheme.spacing.gutter),
    ) {
        BasicText(
            text = "Initializing",
            style = InkTheme.typography.body,
        )
    }
}

/**
 * Displayed when the device is not connected and has no internet connection.
 */
@Composable
fun Disconnected() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(InkTheme.spacing.gutter),
    ) {
        BasicText(
            text = "No internet connection",
            style = InkTheme.typography.body,
        )
    }
}

/**
 * Displayed when the device is connected but not yet configured.
 */
@Composable
fun AwaitingConfig(ips: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(InkTheme.spacing.gutter),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BasicText(
                text = "No data",
                style = InkTheme.typography.body,
            )
            BasicText(
                text = ips,
                style = InkTheme.typography.caption,
            )
        }
    }
}
