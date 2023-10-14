package com.inkapplications.glassconsole.structures

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * UI element configuration to be displayed on the screen.
 */
@Serializable(with = DisplayItem.Serializer::class)
sealed interface DisplayItem {
    /**
     * Polymorphic schema for [DisplayItem] objects.
     */
    @Serializable
    private data class JsonSchema(
        val type: String,
        val text: String? = null,
        val url: String? = null,
        val indicator: Indicator? = null,
    )

    /**
     * Polymorphic serializer for [DisplayItem] objects via the [JsonSchema] class.
     */
    object Serializer: KSerializer<DisplayItem> {
        private val delegate = JsonSchema.serializer()
        override val descriptor: SerialDescriptor = delegate.descriptor

        override fun deserialize(decoder: Decoder): DisplayItem {
            val schema = delegate.deserialize(decoder)
            return when (schema.type) {
                "h1" -> H1(schema.text!!)
                "h2" -> H2(schema.text!!)
                "h3" -> H3(schema.text!!)
                "body" -> Body(schema.text!!)
                "button" -> Button(schema.text!!, schema.url!!, schema.indicator ?: Indicator.Nominal)
                else -> throw IllegalArgumentException("Unknown type: ${schema.type}")
            }
        }

        override fun serialize(encoder: Encoder, value: DisplayItem) {
            val schema = when (value) {
                is H1 -> JsonSchema("h1", value.text)
                is H2 -> JsonSchema("h2", value.text)
                is H3 -> JsonSchema("h3", value.text)
                is Body -> JsonSchema("body", value.text)
                is Button -> JsonSchema("button", value.text, value.url)
            }
            delegate.serialize(encoder, schema)
        }
    }
}
