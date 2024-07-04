package com.inkapplications.glassconsole.client.pin

import com.inkapplications.glassconsole.structures.pin.ChallengeResponse
import com.inkapplications.glassconsole.structures.pin.Nonce
import com.inkapplications.glassconsole.structures.pin.Pin
import com.inkapplications.glassconsole.structures.pin.Psk
import kotlinx.datetime.Instant

/**
 * Validates PIN entry using a PSK and challenge data.
 */
interface PinValidator {
    /**
     * Validate a PIN using a witness and generate a challenge response.
     *
     * @throws IllegalArgumentException if the PIN is invalid.
     */
    fun validate(
        psk: Psk,
        witness: ChallengeResponse,
        challengeNonce: Nonce,
        pin: Pin
    ): ChallengeResponse

    /**
     * Generate a challenge response for a given PIN.
     */
    fun digest(
        psk: Psk,
        pin: Pin,
        timestamp: Instant,
        nonce: Nonce
    ): ChallengeResponse
}

