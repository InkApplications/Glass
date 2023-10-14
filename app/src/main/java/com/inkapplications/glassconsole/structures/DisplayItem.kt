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
     * Large header text
     */
    data class H1(
        val text: String,
    ): DisplayItem

    /**
     * A button that can be pressed to invoke a particular action.
     */
    data class Button(
        val text: String,
        val url: String,
    ): DisplayItem

    /**
     * Polymorphic schema for [DisplayItem] objects.
     */
    @Serializable
    private data class JsonSchema(
        val type: String,
        val text: String? = null,
        val url: String? = null,
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
                "button" -> Button(schema.text!!, schema.url!!)
                else -> throw IllegalArgumentException("Unknown type: ${schema.type}")
            }
        }

        override fun serialize(encoder: Encoder, value: DisplayItem) {
            val schema = when (value) {
                is H1 -> JsonSchema("h1", value.text)
                is Button -> JsonSchema("button", value.text, value.url)
            }
            delegate.serialize(encoder, schema)
        }
    }
}
