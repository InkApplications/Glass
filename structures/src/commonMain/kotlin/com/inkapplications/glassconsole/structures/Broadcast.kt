package com.inkapplications.glassconsole.structures

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Audible feedback announced from the display speakers.
 */
@Serializable(with = Broadcast.Serializer::class)
sealed interface Broadcast {
    data class Ping(
        val indicator: Indicator,
    ): Broadcast

    @Serializable
    private data class Schema(
        val type: String,
        val indicator: Indicator? = null,
    )

    object Serializer: KSerializer<Broadcast> {
        private val delegate = Schema.serializer()
        override val descriptor: SerialDescriptor = delegate.descriptor

        override fun deserialize(decoder: Decoder): Broadcast {
            val schema = delegate.deserialize(decoder)
            return when (schema.type) {
                "ping" -> Ping(
                    indicator = schema.indicator ?: Indicator.Nominal,
                )
                else -> throw IllegalArgumentException("Unknown type: ${schema.type}")
            }
        }

        override fun serialize(encoder: Encoder, value: Broadcast) {
            val schema = when (value) {
                is Ping -> Schema(type = "ping", indicator = value.indicator)
            }
            delegate.serialize(encoder, schema)
        }
    }
}
