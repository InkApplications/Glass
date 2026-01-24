package com.inkapplications.glassconsole.client.pin

import com.inkapplications.glassconsole.structures.pin.Psk

/**
 * Generates random PSK values.
 */
interface PskGenerator {
    fun generate(): Psk
}
