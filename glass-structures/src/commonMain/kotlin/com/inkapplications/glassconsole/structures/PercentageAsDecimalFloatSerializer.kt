package com.inkapplications.glassconsole.structures

import inkapplications.spondee.scalar.Percentage
import inkapplications.spondee.scalar.decimalPercentage
import inkapplications.spondee.structure.toFloat
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Serializes a percentage as a fractional decimal float.
 *
 * ie. 0.5 for 50%
 */
class PercentageAsDecimalFloatSerializer: KSerializer<Percentage> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Percentage", PrimitiveKind.FLOAT)

    override fun deserialize(decoder: Decoder): Percentage {
        return decoder.decodeFloat().decimalPercentage
    }

    override fun serialize(encoder: Encoder, value: Percentage) {
        encoder.encodeFloat(value.toDecimal().toFloat())
    }
}
