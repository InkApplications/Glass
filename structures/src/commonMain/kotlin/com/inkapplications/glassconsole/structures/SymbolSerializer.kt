package com.inkapplications.glassconsole.structures

import ink.ui.structures.Symbol
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind.STRING
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Serializes an icon as a string descriptior
 */
internal class SymbolSerializer(): KSerializer<Symbol> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Symbol", STRING)

    override fun deserialize(decoder: Decoder): Symbol {
        val string = decoder.decodeString()
        return Symbol(string)
    }

    override fun serialize(encoder: Encoder, value: Symbol) {
        encoder.encodeString(value.key)
    }
}
