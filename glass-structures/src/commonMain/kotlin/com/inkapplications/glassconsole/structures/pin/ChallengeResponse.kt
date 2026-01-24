package com.inkapplications.glassconsole.structures.pin

import com.inkapplications.glassconsole.structures.MillisecondsInstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 * A completed digest and its parameters for a PIN challenge.
 */
@Serializable
data class ChallengeResponse(
    /**
     * Nonce used to create the digest.
     */
    val nonce: Nonce,

    /**
     * Timestamp used for the digest.
     */
    @Serializable(with = MillisecondsInstantSerializer::class)
    val timestamp: Instant,

    /**
     * The output hash of the nonce, timestamp, PSK and PIN, as a hex string.
     */
    val digest: String,
)
