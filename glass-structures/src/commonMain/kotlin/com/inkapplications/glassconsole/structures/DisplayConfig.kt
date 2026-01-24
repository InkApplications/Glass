package com.inkapplications.glassconsole.structures

import com.inkapplications.glassconsole.structures.BacklightConfig.Auto
import kotlinx.serialization.Serializable
import kotlin.time.Duration

/**
 * Full configuration instructions for what to display on the screen.
 */
@Serializable
data class DisplayConfig(
    /**
     * An amount of time to display the UI before it is removed.
     *
     * This is used to help prevent a stale UI from being displayed if
     * the driving application crashes or fails to provide an update.
     */
    @Serializable(with = SecondsDurationSerializer::class)
    val expiration: Duration? = null,

    /**
     * Backlight configuration for the display.
     */
    val backlight: BacklightConfig = Auto,
)
