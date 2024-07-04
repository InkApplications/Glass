package com.inkapplications.glassconsole.client.pin

import com.inkapplications.glassconsole.structures.pin.ChallengeResponse
import com.inkapplications.glassconsole.structures.pin.Nonce
import com.inkapplications.glassconsole.structures.pin.Pin
import com.inkapplications.glassconsole.structures.pin.Psk
import com.ionspin.kotlin.crypto.hash.Hash
import com.ionspin.kotlin.crypto.util.encodeToUByteArray
import com.ionspin.kotlin.crypto.util.toHexString
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

/**
 * Validates PIN entry with a SHA-256 hash.
 *
 * This uses the following format to create a response digest:
 *
 *     SHA256(psk + pin + timestamp + nonce)
 */
@OptIn(ExperimentalUnsignedTypes::class)
internal class Sha256PinValidator(
    private val clock: Clock,
): PinValidator {
    override fun validate(
        psk: Psk,
        witness: ChallengeResponse,
        challengeNonce: Nonce,
        pin: Pin
    ): ChallengeResponse {
        val witnessConfirmation = digest(
            psk = psk,
            pin = pin,
            timestamp = witness.timestamp,
            nonce = witness.nonce,
        )

        if (witnessConfirmation != witness) {
            throw IllegalArgumentException("Invalid PIN")
        }

        return digest(psk, pin, clock.now(), challengeNonce)
    }

    override fun digest(
        psk: Psk,
        pin: Pin,
        timestamp: Instant,
        nonce: Nonce
    ): ChallengeResponse {
        return (psk.value + pin.value + timestamp.toEpochMilliseconds()  + nonce.value)
            .encodeToUByteArray()
            .let(Hash::sha256)
            .toHexString()
            .let {
                ChallengeResponse(digest = it, timestamp = timestamp, nonce = nonce)
            }
    }
}
