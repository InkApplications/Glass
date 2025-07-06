package com.inkapplications.glassconsole.structures

import ink.ui.structures.Sentiment
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
        val sentiment: Sentiment,
    ): Broadcast

    data class Announcement(
        val text: String,
        val sentiment: Sentiment? = null,
    ): Broadcast

    @Serializable
    private data class Schema(
        val type: String,
        val sentiment: Sentiment? = null,
        val text: String? = null,
    )

    object Serializer: KSerializer<Broadcast> {
        private val delegate = Schema.serializer()
        override val descriptor: SerialDescriptor = delegate.descriptor

        override fun deserialize(decoder: Decoder): Broadcast {
            val schema = delegate.deserialize(decoder)
            return when (schema.type) {
                "ping" -> Ping(
                    sentiment = schema.sentiment ?: Sentiment.Nominal,
                )
                "announcement" -> Announcement(
                    text = schema.text!!,
                    sentiment = schema.sentiment,
                )
                else -> throw IllegalArgumentException("Unknown type: ${schema.type}")
            }
        }

        override fun serialize(encoder: Encoder, value: Broadcast) {
            val schema = when (value) {
                is Ping -> Schema(type = "ping", sentiment = value.sentiment)
                is Announcement -> Schema(type = "announcement", text = value.text, sentiment = value.sentiment)
            }
            delegate.serialize(encoder, schema)
        }
    }
}
