package com.inkapplications.glassconsole.client.remote

import com.inkapplications.glassconsole.structures.Broadcast
import com.inkapplications.glassconsole.structures.DisplayConfig

/**
 * HTTP Client for sending display instructions.
 */
interface GlassHttpClient {
    /**
     * Send a new display configuration to the server.
     *
     * @param config The new configuration data to send.
     * @param host The IP or hostname of the device to be configured
     * @param port The port that the display server is running on for the display device (default: 8080)
     */
    suspend fun updateDisplay(
        config: DisplayConfig,
        host: String,
        port: Int = 8080,
    )

    /**
     * Send audible feedback to be played on the display
     *
     * @param broadcast Configuration for the broadcast to be played.
     * @param host The IP or hostname of the device to be configured
     * @param port The port that the display server is running on for the display device (default: 8080)
     */
    suspend fun broadcast(
        broadcast: Broadcast,
        host: String,
        port: Int = 8080,
    )
}

