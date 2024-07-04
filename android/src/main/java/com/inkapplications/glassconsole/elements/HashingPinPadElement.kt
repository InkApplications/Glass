package com.inkapplications.glassconsole.elements

import com.inkapplications.glassconsole.structures.pin.ChallengeResponse
import com.inkapplications.glassconsole.structures.pin.Nonce
import ink.ui.structures.elements.UiElement

/**
 * A [PinPadElement] that hashes the input and sends it to a server.
 */
data class HashingPinPadElement(
    val challengeNonce: Nonce,
    val witness: ChallengeResponse,
    val onSuccess: (ChallengeResponse) -> Unit,
): UiElement.Interactive
