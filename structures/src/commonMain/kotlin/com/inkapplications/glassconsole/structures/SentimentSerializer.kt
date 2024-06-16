package com.inkapplications.glassconsole.structures

import ink.ui.structures.Sentiment
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal object SentimentSerializer: KSerializer<Sentiment> {
    override val descriptor: SerialDescriptor = String.serializer().descriptor
    override fun deserialize(decoder: Decoder): Sentiment {
        val value = String.serializer().deserialize(decoder)
        return when (value) {
            "nominal" -> Sentiment.Nominal
            "primary" -> Sentiment.Primary
            "positive" -> Sentiment.Positive
            "negative" -> Sentiment.Negative
            "caution" -> Sentiment.Caution
            "idle" -> Sentiment.Idle
            else -> throw IllegalArgumentException("Unknown sentiment: $value")
        }
    }

    override fun serialize(encoder: Encoder, value: Sentiment) {
        val string = when (value) {
            Sentiment.Nominal -> "nominal"
            Sentiment.Primary -> "primary"
            Sentiment.Positive -> "positive"
            Sentiment.Negative -> "negative"
            Sentiment.Caution -> "caution"
            Sentiment.Idle -> "idle"
        }
        String.serializer().serialize(encoder, string)
    }
}
