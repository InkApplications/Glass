package com.inkapplications.glassconsole.structures

import com.inkapplications.glassconsole.structures.pin.ChallengeResponse
import com.inkapplications.glassconsole.structures.pin.Nonce
import ink.ui.structures.Positioning

/**
 * A pin pad that can be used to validate an arbitrary pin code.
 */
data class PinPadItem(
    override val position: Positioning = Positioning.Center,
    override val span: Int = 1,
    /**
     * A witness response used to locally validate pin entry.
     */
    val witness: ChallengeResponse,
    /**
     * The challenge to return to the server once a pin is entered.
     */
    val challengeNonce: Nonce,
    /**
     * The URL to POST the pin challenge response to after confirmation.
     */
    val callbackUrl: String,
): DisplayItem
