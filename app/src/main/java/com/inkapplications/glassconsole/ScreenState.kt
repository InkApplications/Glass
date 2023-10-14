package com.inkapplications.glassconsole

import com.inkapplications.glassconsole.structures.DisplayConfig

/**
 * Possible states for the main screen display.
 */
sealed interface ScreenState {
    /**
     * Initial screen state before any data has been loaded.
     */
    object Initial: ScreenState

    /**
     * Screen State when the device has no network connection.
     */
    object NoConnection: ScreenState

    /**
     * Screen state shown when the device is listening for a connection.
     */
    data class NoData(
        val ips: String,
    ): ScreenState

    /**
     * State used when the application has received a configuration.
     *
     * @param config The configuration data to be displayed.
     */
    data class Configured(
        val config: DisplayConfig,
        val connected: Boolean,
    ): ScreenState
}
