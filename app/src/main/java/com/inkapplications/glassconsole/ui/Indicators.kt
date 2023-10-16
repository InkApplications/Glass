package com.inkapplications.glassconsole.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.inkapplications.glassconsole.structures.Indicator
import com.inkapplications.glassconsole.ui.theme.InkTheme

/**
 * Provides a UI color associated with an indicator.
 */
val Indicator.color
    @Composable
    @ReadOnlyComposable
    get() = when (this) {
        Indicator.Nominal -> InkTheme.color.foreground
        Indicator.Primary -> InkTheme.color.accent
        Indicator.Positive -> InkTheme.color.success
        Indicator.Danger -> InkTheme.color.warning
        Indicator.Negative -> InkTheme.color.error
        Indicator.Idle -> InkTheme.color.dimForeground
    }
