package com.inkapplications.glassconsole

import com.inkapplications.glassconsole.structures.DisplayConfig

/**
 * Possible states for the main screen display.
 */
sealed interface ScreenState {
    /**
     * State used when the application initially loads and has not yet
     * been configured.
     */
    object NoData: ScreenState

    /**
     * State used when the application has received a configuration.
     *
     * @param config The configuration data to be displayed.
     */
    data class Configured(val config: DisplayConfig): ScreenState
}
