package com.inkapplications.glassconsole

import com.inkapplications.glassconsole.server.DisplayServer
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

/**
 * Object graph used application-wide.
 */
object ApplicationModule {
    val displayServer = DisplayServer()
    val httpClient = HttpClient(CIO) {}
}
