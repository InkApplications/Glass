package com.inkapplications.glassconsole.structures

import kotlinx.serialization.Serializable
import kotlin.time.Duration

/**
 * Full configuration instructions for what to display on the screen.
 */
@Serializable
data class DisplayConfig(
    /**
     * A list of UI elements to be displayed.
     */
    val items: List<DisplayItem>,

    /**
     * The layout to use for the display.
     */
    val layout: LayoutType = LayoutType.VerticalGrid(1),

    /**
     * An amount of time to display the UI before it is removed.
     *
     * This is used to help prevent a stale UI from being displayed if
     * the driving application crashes or fails to provide an update.
     */
    @Serializable(with = SecondsDurationSerializer::class)
    val expiration: Duration? = null,
)
