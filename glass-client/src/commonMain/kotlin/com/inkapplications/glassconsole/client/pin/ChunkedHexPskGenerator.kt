package com.inkapplications.glassconsole.client.pin

import com.inkapplications.glassconsole.structures.pin.Psk
import com.ionspin.kotlin.crypto.util.LibsodiumRandom
import com.ionspin.kotlin.crypto.util.toHexString

/**
 * Generates random PSK values as a hex string split into readable chunks.
 */
class ChunkedHexPskGenerator(
    private val length: Int = 12,
    private val chunkSize: Int = 4
): PskGenerator {
    override fun generate(): Psk {
        return LibsodiumRandom.buf(length)
            .toHexString()
            .uppercase()
            .chunked(chunkSize)
            .joinToString("-")
            .let(::Psk)
    }
}
