package com.inkapplications.glassconsole

import com.inkapplications.glassconsole.server.DisplayServer

/**
 * Object graph used application-wide.
 */
object ApplicationModule {
    val displayServer = DisplayServer()
}
