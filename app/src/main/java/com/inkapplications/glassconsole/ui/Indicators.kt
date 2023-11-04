package com.inkapplications.glassconsole.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.inkapplications.glassconsole.R
import com.inkapplications.glassconsole.structures.Indicator
import com.inkapplications.glassconsole.ui.theme.InkTheme

/**
 * Provides a UI color associated with an indicator.
 */
val Indicator.color
    @Composable
    @ReadOnlyComposable
    get() = when (this) {
        Indicator.Primary -> InkTheme.color.accent
        Indicator.Positive -> InkTheme.color.success
        Indicator.Danger -> InkTheme.color.warning
        Indicator.Negative -> InkTheme.color.error
        Indicator.Idle -> InkTheme.color.dimForeground
        else -> InkTheme.color.foreground
    }

val Indicator.sound
    get() = when (this) {
        Indicator.Primary -> R.raw.primary
        Indicator.Positive -> R.raw.positive
        Indicator.Danger -> R.raw.danger
        Indicator.Negative -> R.raw.negative
        Indicator.Idle -> R.raw.idle
        else -> R.raw.nominal
    }
