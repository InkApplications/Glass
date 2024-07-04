package com.inkapplications.glassconsole.client.pin

import com.inkapplications.glassconsole.structures.pin.Nonce
import com.ionspin.kotlin.crypto.util.LibsodiumRandom
import com.ionspin.kotlin.crypto.util.toHexString

@OptIn(ExperimentalUnsignedTypes::class)
internal class RandomHexNonceGenerator(
    private val length: Int = 16,
): NonceGenerator {
    override fun generateNonce(): Nonce {
        return LibsodiumRandom.buf(length)
            .toHexString()
            .let(::Nonce)
    }
}
