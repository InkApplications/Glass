package com.inkapplications.glassconsole.structures

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Supported Layout types for the collection of items on the display.
 */
@Serializable(with = LayoutType.Serializer::class)
sealed interface LayoutType {
    /**
     * Display the items in a grid column scrolling vertically.
     */
    data class VerticalGrid(val columns: Int): LayoutType

    @Serializable
    private data class Schema(
        val type: String,
        val columns: Int? = null,
    )

    object Serializer: KSerializer<LayoutType> {
        private val delegate = Schema.serializer()
        override val descriptor: SerialDescriptor = delegate.descriptor

        override fun deserialize(decoder: Decoder): LayoutType {
            val schema = delegate.deserialize(decoder)
            return when (schema.type) {
                "vertical-grid" -> VerticalGrid(schema.columns!!)
                else -> throw IllegalArgumentException("Unknown type: ${schema.type}")
            }
        }

        override fun serialize(encoder: Encoder, value: LayoutType) {
            val schema = when (value) {
                is VerticalGrid -> Schema(type = "vertical-grid", columns = value.columns)
            }
            delegate.serialize(encoder, schema)
        }
    }
}
