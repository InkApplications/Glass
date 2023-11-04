package com.inkapplications.glassconsole.structures

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Serializes a duration as a whole number of seconds.
 */
internal object SecondsDurationSerializer: KSerializer<Duration> {
    private val delegate = Long.serializer()
    override val descriptor: SerialDescriptor = delegate.descriptor

    override fun deserialize(decoder: Decoder): Duration {
        return decoder.decodeSerializableValue(delegate).seconds
    }

    override fun serialize(encoder: Encoder, value: Duration) {
        encoder.encodeSerializableValue(delegate, value.inWholeSeconds)
    }
}
