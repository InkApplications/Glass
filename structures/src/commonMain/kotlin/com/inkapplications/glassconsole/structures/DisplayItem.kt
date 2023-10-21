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
        val action: Action? = null,
        val latching: Boolean? = null,
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
                "h1" -> TextItem.H1(schema.text!!)
                "h2" -> TextItem.H2(schema.text!!)
                "h3" -> TextItem.H3(schema.text!!)
                "body" -> TextItem.Body(schema.text!!)
                "button" -> ButtonItem(
                    text = schema.text!!,
                    action = schema.action!!,
                    latching = schema.latching ?: false,
                    indicatorColor = schema.indicator ?: Indicator.Nominal
                )
                "status" -> StatusItem(
                    text = schema.text!!,
                    indicator = schema.indicator!!,
                )
                else -> throw IllegalArgumentException("Unknown type: ${schema.type}")
            }
        }

        override fun serialize(encoder: Encoder, value: DisplayItem) {
            val schema = when (value) {
                is TextItem.H1 -> JsonSchema(type = "h1", text = value.text)
                is TextItem.H2 -> JsonSchema(type = "h2", text = value.text)
                is TextItem.H3 -> JsonSchema(type = "h3", text = value.text)
                is TextItem.Body -> JsonSchema(type = "body", text = value.text)
                is ButtonItem -> JsonSchema(
                    type = "button",
                    text = value.text,
                    action = value.action,
                    latching = value.latching,
                )
                is StatusItem -> JsonSchema(
                    type = "status",
                    text = value.text,
                    indicator = value.indicator,
                )
            }
            delegate.serialize(encoder, schema)
        }
    }
}
