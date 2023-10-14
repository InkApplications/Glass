package com.inkapplications.glassconsole

import com.inkapplications.DisplayApplication
import com.inkapplications.glassconsole.server.DisplayServer
import com.inkapplications.glassconsole.server.IpProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

/**
 * Object graph used application-wide.
 */
object ApplicationModule {
    lateinit var application: DisplayApplication

    val displayServer = DisplayServer()
    val httpClient = HttpClient(CIO) {}
    val ipProvider by lazy {
        IpProvider(
            connectivityManager = application.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager,
            context = application
        )
    }
}
