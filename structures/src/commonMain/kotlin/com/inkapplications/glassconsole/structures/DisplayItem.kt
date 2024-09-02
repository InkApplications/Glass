package com.inkapplications.glassconsole.structures

import com.inkapplications.glassconsole.structures.pin.ChallengeResponse
import com.inkapplications.glassconsole.structures.pin.Nonce
import ink.ui.structures.Positioning
import ink.ui.structures.Sentiment
import ink.ui.structures.Symbol
import ink.ui.structures.TextStyle
import ink.ui.structures.elements.*
import kotlinx.datetime.Instant
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * UI element configuration to be displayed on the screen.
 */
@Serializable(with = DisplayItem.Serializer::class)
sealed interface DisplayItem: Spanable, Positionable {
    /**
     * Polymorphic schema for [DisplayItem] objects.
     */
    @Serializable
    private data class JsonSchema(
        val type: String,
        val span: Int,
        @Serializable(with = PositionSerializer::class)
        val position: Positioning,
        val text: String? = null,
        val action: Action? = null,
        val latching: Boolean? = null,
        @Serializable(with = SentimentSerializer::class)
        val sentiment: Sentiment? = null,
        val progress: Float? = null,
        val witness: String? = null,
        val witnessNonce: Nonce? = null,
        @Serializable(with = MillisecondsInstantSerializer::class)
        val witnessTimestamp: Instant? = null,
        val challengeNonce: Nonce? = null,
        val callbackUrl: String? = null,
        @Serializable(with = SymbolSerializer::class)
        val leadingSymbol: Symbol? = null,
        @Serializable(with = SymbolSerializer::class)
        val trailingSymbol: Symbol? = null,
        @Serializable(with = SymbolSerializer::class)
        val symbol: Symbol? = null,
    )

    object Serializer: KSerializer<DisplayItem> {
        private val surrogate = JsonSchema.serializer()
        override val descriptor: SerialDescriptor = surrogate.descriptor

        private fun Sentiment?.orDefault() = this ?: Sentiment.Nominal

        override fun deserialize(decoder: Decoder): DisplayItem {
            val schema = surrogate.deserialize(decoder)

            return when (schema.type) {
                "h1" -> StaticElementItem(
                    element = TextElement(
                        text = schema.text!!,
                        style = TextStyle.H1,
                    ),
                    position = schema.position,
                    span = schema.span,
                )
                "h2" -> StaticElementItem(
                    element = TextElement(
                        text = schema.text!!,
                        style = TextStyle.H2,
                    ),
                    position = schema.position,
                    span = schema.span,
                )
                "h3" -> StaticElementItem(
                    element = TextElement(
                        text = schema.text!!,
                        style = TextStyle.H3,
                    ),
                    position = schema.position,
                    span = schema.span,
                )
                "body" -> StaticElementItem(
                    element = TextElement(
                        text = schema.text!!,
                        style = TextStyle.Body,
                    ),
                    position = schema.position,
                    span = schema.span,
                )
                "caption" -> StaticElementItem(
                    element = TextElement(
                        text = schema.text!!,
                        style = TextStyle.Caption,
                    ),
                    position = schema.position,
                    span = schema.span,
                )
                "status" -> StaticElementItem(
                    element = StatusIndicatorElement(
                        text = schema.text!!,
                        sentiment = schema.sentiment.orDefault(),
                    ),
                    position = schema.position,
                    span = schema.span,
                )
                "throbber" -> StaticElementItem(
                    element = ThrobberElement(
                        caption = schema.text,
                        sentiment = schema.sentiment.orDefault(),
                    ),
                    position = schema.position,
                    span = schema.span,
                )
                "progress" -> StaticElementItem(
                    element = if (schema.progress != null) ProgressElement.Determinate(
                        progress = schema.progress,
                        caption = schema.text,
                        sentiment = schema.sentiment.orDefault(),
                    ) else ProgressElement.Indeterminate(
                        caption = schema.text,
                        sentiment = schema.sentiment.orDefault(),
                    ),
                    position = schema.position,
                    span = schema.span,
                )
                "spacer" -> StaticElementItem(
                    element = EmptyElement,
                    position = schema.position,
                    span = schema.span,
                )
                "icon" -> StaticElementItem(
                    element = IconElement(
                        symbol = schema.symbol!!,
                        sentiment = schema.sentiment.orDefault(),
                    ),
                    position = schema.position,
                    span = schema.span,
                )
                "button" -> ButtonItem(
                    text = schema.text!!,
                    action = schema.action!!,
                    latching = schema.latching ?: false,
                    sentiment = schema.sentiment.orDefault(),
                    position = schema.position,
                    span = schema.span,
                    leadingSymbol = schema.leadingSymbol,
                    trailingSymbol = schema.trailingSymbol,
                )
                "pinpad" -> PinPadItem(
                    challengeNonce = schema.challengeNonce!!,
                    witness = ChallengeResponse(
                        nonce = schema.witnessNonce!!,
                        timestamp = schema.witnessTimestamp!!,
                        digest = schema.witness!!,
                    ),
                    callbackUrl = schema.callbackUrl!!,
                    position = schema.position,
                    span = schema.span,
                )
                else -> throw IllegalArgumentException("Unknown type: ${schema.type}")
            }
        }

        override fun serialize(encoder: Encoder, value: DisplayItem) {
            val schema = when (value) {
                is StaticElementItem -> JsonSchema(
                    type = when (value.element) {
                        is TextElement -> when (value.element.style) {
                            TextStyle.H1 -> "h1"
                            TextStyle.H2 -> "h2"
                            TextStyle.H3 -> "h3"
                            TextStyle.Body -> "body"
                            TextStyle.Caption -> "caption"
                        }
                        is StatusIndicatorElement -> "status"
                        is ThrobberElement -> "throbber"
                        is ProgressElement -> "progress"
                        is EmptyElement -> "spacer"
                        is IconElement -> "icon"
                        else -> throw IllegalArgumentException("Unsupported element type: ${value.element::class}")
                    },
                    span = value.span,
                    position = value.position,
                    text = when (value.element) {
                        is TextElement -> value.element.text
                        is StatusIndicatorElement -> value.element.text
                        is ThrobberElement -> value.element.caption
                        is ProgressElement -> value.element.caption
                        is EmptyElement -> null
                        else -> null
                    },
                    sentiment = when (value.element) {
                        is StatusIndicatorElement -> value.element.sentiment
                        is ThrobberElement -> value.element.sentiment
                        is ProgressElement -> value.element.sentiment
                        else -> Sentiment.Nominal
                    },
                    symbol = when (value.element) {
                        is IconElement -> value.element.symbol
                        else -> null
                    },
                    progress = when (value.element) {
                        is ProgressElement.Determinate -> value.element.progress
                        else -> null
                    },
                )
                is ButtonItem -> JsonSchema(
                    type = "button",
                    text = value.text,
                    action = value.action,
                    latching = value.latching,
                    sentiment = value.sentiment,
                    span = value.span,
                    position = value.position,
                    leadingSymbol = value.leadingSymbol,
                    trailingSymbol = value.trailingSymbol,
                )
                is PinPadItem -> JsonSchema(
                    type = "pinpad",
                    challengeNonce = value.challengeNonce,
                    witness = value.witness.digest,
                    witnessNonce = value.witness.nonce,
                    witnessTimestamp = value.witness.timestamp,
                    callbackUrl = value.callbackUrl,
                    span = value.span,
                    position = value.position,
                )
            }

            surrogate.serialize(encoder, schema)
        }
    }
}

