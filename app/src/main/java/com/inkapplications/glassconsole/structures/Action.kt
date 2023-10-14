package com.inkapplications.glassconsole.structures

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Models the possible actions that can be triggered from a UI element.
 */
@Serializable(with = Action.Serializer::class)
sealed interface Action {
    data class Get(val url: String): Action
    data class Put(val url: String, val body: String): Action
    data class Post(val url: String, val body: String): Action
    data class Delete(val url: String): Action

    @Serializable
    data class Schema(
        val type: String,
        val url: String,
        val body: String? = null,
    )

    object Serializer: KSerializer<Action> {
        private val delegate = Schema.serializer()
        override val descriptor: SerialDescriptor = delegate.descriptor

        override fun deserialize(decoder: Decoder): Action {
            val schema = delegate.deserialize(decoder)
            return when (schema.type) {
                "get" -> Get(schema.url)
                "put" -> Put(schema.url, schema.body!!)
                "post" -> Post(schema.url, schema.body!!)
                "delete" -> Delete(schema.url)
                else -> throw IllegalArgumentException("Unknown type: ${schema.type}")
            }
        }

        override fun serialize(encoder: Encoder, value: Action) {
            val schema = when (value) {
                is Get -> Schema("get", value.url)
                is Put -> Schema("put", value.url, value.body)
                is Post -> Schema("post", value.url, value.body)
                is Delete -> Schema("delete", value.url)
            }

            delegate.serialize(encoder, schema)
        }
    }
}
