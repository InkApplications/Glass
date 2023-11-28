package com.inkapplications.glassconsole

import com.inkapplications.glassconsole.client.ActionClient
import com.inkapplications.glassconsole.server.DisplayServer
import com.inkapplications.glassconsole.server.IpProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import kimchi.Kimchi
import regolith.init.InitRunner
import regolith.init.RegolithInitRunner
import regolith.init.TargetManager
import regolith.processes.daemon.DaemonInitializer

/**
 * Object graph used application-wide.
 */
class ApplicationModule(
    private val application: DisplayApplication,
) {
    val displayServer = DisplayServer()
    private val broadcaster = Broadcaster(displayServer, application, Kimchi)
    private val daemonInitializer = DaemonInitializer(
        daemons = listOf(displayServer, broadcaster),
    )
    private val regolith = RegolithInitRunner(
        initializers = listOf(daemonInitializer),
    )
    val initRunner: InitRunner = regolith
    val targetManager: TargetManager = regolith

    val httpClient = HttpClient(OkHttp) {}
    val actionClient = ActionClient(httpClient)

    val ipProvider = IpProvider(
        connectivityManager = application.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager,
        context = application
    )
}
