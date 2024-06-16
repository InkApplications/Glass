package com.inkapplications.glassconsole.structures

import ink.ui.structures.Positioning
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal object PositionSerializer: KSerializer<Positioning> {
    private val surrogate = String.serializer()
    override val descriptor: SerialDescriptor = surrogate.descriptor

    override fun deserialize(decoder: Decoder): Positioning {
        val value = surrogate.deserialize(decoder)
        return when (value) {
            "start" -> Positioning.Start
            "center" -> Positioning.Center
            else -> throw IllegalArgumentException("Unknown position: $value")
        }
    }

    override fun serialize(encoder: Encoder, value: Positioning) {
        val string = when (value) {
            Positioning.Start -> "start"
            Positioning.Center -> "center"
        }
        surrogate.serialize(encoder, string)
    }
}
