package com.inkapplications.glassconsole

import com.inkapplications.glassconsole.structures.DisplayConfig
import com.inkapplications.glassconsole.structures.pin.Psk

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

    interface Connected: ScreenState
    {
        val ips: List<String>
    }

    /**
     * Set-up screen to display the saved PSK on the device.
     *
     * This is only displayed once when the application is first launched.
     */
    data class ShowPsk(
        val psk: Psk,
        val onDismiss: () -> Unit,
    ): ScreenState

    /**
     * Screen state shown when the device is listening for a connection.
     */
    data class NoData(
        override val ips: List<String>,
    ): Connected

    /**
     * State used when the application has received a configuration.
     *
     * @param config The configuration data to be displayed.
     */
    data class Configured(
        val config: DisplayConfig,
        override val ips: List<String>,
    ): Connected
}
