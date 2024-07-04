package com.inkapplications.glassconsole.client.pin

import com.inkapplications.glassconsole.structures.pin.Psk
import kotlinx.coroutines.flow.Flow

/**
 * Saves and retrieves a single PSK tied to a glass application.
 */
interface PskAccess {
    /**
     * Get the current PSK value, if set
     */
    val psk: Flow<Psk?>

    /**
     * Save a new PSK value, overwriting any existing value.
     */
    suspend fun setPsk(psk: Psk)
}
