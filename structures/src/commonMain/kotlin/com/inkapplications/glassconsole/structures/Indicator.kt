package com.inkapplications.glassconsole.structures

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Signifier that hints at the behavior of an element.
 */
@Serializable
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
}
