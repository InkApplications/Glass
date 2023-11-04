package com.inkapplications.glassconsole.structures

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * Strategies for positioning an element's content within its cell in the layout.
 */
@Serializable
@JvmInline
value class Position(val key: String) {
    companion object {
        val Default = Position("default")
        val Centered = Position("centered")
    }
}
