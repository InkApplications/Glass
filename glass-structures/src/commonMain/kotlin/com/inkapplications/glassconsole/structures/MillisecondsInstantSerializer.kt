package com.inkapplications.glassconsole.structures

import kotlinx.datetime.Instant
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal class MillisecondsInstantSerializer: KSerializer<Instant> {
    private val delegate = Long.serializer()
    override val descriptor: SerialDescriptor = delegate.descriptor

    override fun deserialize(decoder: Decoder): Instant {
        return Instant.fromEpochMilliseconds(decoder.decodeSerializableValue(delegate))
    }

    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeSerializableValue(delegate, value.toEpochMilliseconds())
    }
}
