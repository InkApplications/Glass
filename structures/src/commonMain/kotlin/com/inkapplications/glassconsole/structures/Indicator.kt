package com.inkapplications.glassconsole.structures

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * Signifier that hints at the behavior of an element.
 */
@JvmInline
@Serializable
value class Indicator(val key: String) {
    companion object {
        /**
         * Use default signifiers
         */
        val Nominal = Indicator("nominal")

        /**
         * Signify that this element is a main element amongst nominal emelents.
         */
        val Primary = Indicator("primary")

        /**
         * Signify that this element is in a successful or good state.
         */
        val Positive = Indicator("positive")

        /**
         * Indicate a potential danger for an element.
         */
        val Danger = Indicator("danger")

        /**
         * Indicate that an element is in a bad state.
         */
        val Negative = Indicator("negative")

        /**
         * Indicate that an item is in an inactive state.
         */
        val Idle = Indicator("idle")
    }
}
