package com.inkapplications.glassconsole.structures

import inkapplications.spondee.scalar.Percentage
import inkapplications.spondee.scalar.decimalPercentage
import inkapplications.spondee.structure.toFloat
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Settings for the behavior of the backlight and UI visibility.
 */
@Serializable(with = Serializer::class)
sealed interface BacklightConfig {
    /**
     * Use the device's default behavior for the backlight.
     */
    data object Auto: BacklightConfig

    /**
     * Turn off the backlight.
     *
     * @param tapToWake Whether to allow the user to tap the device to wake it.
     */
    data class Off(
        val tapToWake: Boolean = DEFAULT_TAP_RESET,
    ): BacklightConfig {
        companion object {
            const val DEFAULT_TAP_RESET = true
        }
    }

    /**
     * Set the backlight to a specified brightness.
     *
     * @param brightness The percentage brightness to set the backlight to.
     * @param tapToWake Whether to allow the user to tap the device to wake it.
     */
    data class Fixed(
        val brightness: Percentage,
        val tapToWake: Boolean = DEFAULT_TAP_RESET,
    ): BacklightConfig {
        companion object {
            const val DEFAULT_TAP_RESET = false
        }
    }
}

@Serializable
private class JsonSchema(
    val behavior: String,
    @Serializable(with = PercentageAsDecimalFloatSerializer::class)
    val brightness: Percentage? = null,
    val tapToWake: Boolean? = null,
)

private class Serializer: KSerializer<BacklightConfig> {
    private val backingSerializer = JsonSchema.serializer()
    override val descriptor: SerialDescriptor = backingSerializer.descriptor

    override fun serialize(encoder: Encoder, value: BacklightConfig) {
        val schema = when(value) {
            is BacklightConfig.Auto -> JsonSchema("auto")
            is BacklightConfig.Off -> JsonSchema("off", tapToWake = value.tapToWake)
            is BacklightConfig.Fixed -> JsonSchema("fixed", value.brightness, value.tapToWake)
        }
        backingSerializer.serialize(encoder, schema)
    }

    override fun deserialize(decoder: Decoder): BacklightConfig {
        val schema = backingSerializer.deserialize(decoder)
        return when(schema.behavior) {
            "auto" -> BacklightConfig.Auto
            "off" -> BacklightConfig.Off(
                tapToWake = schema.tapToWake ?: BacklightConfig.Off.DEFAULT_TAP_RESET
            )
            "fixed" -> BacklightConfig.Fixed(
                schema.brightness!!,
                schema.tapToWake ?: BacklightConfig.Fixed.DEFAULT_TAP_RESET
            )
            else -> throw IllegalArgumentException("Unknown backlight behavior: ${schema.behavior}")
        }
    }
}
