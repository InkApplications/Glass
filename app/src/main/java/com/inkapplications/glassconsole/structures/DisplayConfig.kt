package com.inkapplications.glassconsole.structures

import kotlinx.serialization.Serializable

/**
 * Full configuration instructions for what to display on the screen.
 */
@Serializable
data class DisplayConfig(
    /**
     * A list of UI elements to be displayed.
     */
    val items: List<DisplayItem>
)
