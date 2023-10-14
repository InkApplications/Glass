package com.inkapplications.glassconsole.structures

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.inkapplications.glassconsole.ui.theme.InkTheme
import kotlinx.serialization.SerialName

/**
 * Signifier that hints at the behavior of an element.
 */
enum class Indicator {
    /**
     * Use default signifiers
     */
    @SerialName("nominal")
    Nominal,

    /**
     * Signify that this element is a main element amongst nominal emelents.
     */
    @SerialName("primary")
    Primary,

    /**
     * Signify that this element is in a successful or good state.
     */
    @SerialName("positive")
    Positive,

    /**
     * Indicate a potential danger for an element.
     */
    @SerialName("danger")
    Danger,

    /**
     * Indicate that an element is in a bad state.
     */
    @SerialName("negative")
    Negative,

    /**
     * Indicate that an item is in an inactive state.
     */
    @SerialName("idle")
    Idle;

    val color
        @Composable
        @ReadOnlyComposable
        get() = when (this) {
            Nominal -> InkTheme.color.foreground
            Primary -> InkTheme.color.accent
            Positive -> InkTheme.color.success
            Danger -> InkTheme.color.warning
            Negative -> InkTheme.color.error
            Idle -> InkTheme.color.dimForeground
        }
    }
