package com.inkapplications.glassconsole.client.pin

import com.inkapplications.glassconsole.structures.pin.Nonce

/**
 * Creates random nonce values.
 */
interface NonceGenerator {
    fun generateNonce(): Nonce
}
