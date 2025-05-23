package com.inkapplications.glassconsole

import com.inkapplications.glassconsole.client.ActionClient
import com.inkapplications.glassconsole.client.GlassClientModule
import com.inkapplications.glassconsole.renderer.HashingPinPadRenderer
import com.inkapplications.glassconsole.renderer.PinPadRenderer
import com.inkapplications.glassconsole.server.DisplayServer
import com.inkapplications.glassconsole.server.IpProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import kimchi.Kimchi
import kimchi.logger.LogLevel
import kimchi.logger.defaultWriter
import kimchi.logger.withThreshold
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json
import regolith.data.settings.AndroidSettingsModule
import regolith.init.InitRunner
import regolith.init.Initializer
import regolith.init.RegolithInitRunner
import regolith.processes.daemon.DaemonInitializer

/**
 * Object graph used application-wide.
 */
class ApplicationModule(
    application: DisplayApplication,
) {
    private val clock = Clock.System
    val logger = Kimchi.apply {
        addLog(
            if (BuildConfig.DEBUG) defaultWriter
            else defaultWriter.withThreshold(LogLevel.INFO)
        )
    }
    val displayServer = DisplayServer(logger)
    private val kimchiRegolithAdapter = KimchiRegolithAdapter(logger)
    private val settingsModule = AndroidSettingsModule(application)
    val pskAccess = GlassClientModule.createPskAccess(
        settingsAccess = settingsModule.settingsAccess,
    )
    val pskGenerator = GlassClientModule.createPskGenerator()
    private val pinValidator = GlassClientModule.createPinValidator(clock)
    private val hashingPinPadRenderer = HashingPinPadRenderer(
        pinValidator = pinValidator,
        pskAccess = pskAccess,
    )
    val renderers = listOf(PinPadRenderer, hashingPinPadRenderer)

    private val broadcaster = Broadcaster(displayServer, application, logger)
    private val daemonInitializer = DaemonInitializer(
        callbacks = kimchiRegolithAdapter,
        daemons = listOf(displayServer, broadcaster),
    )
    private val regolith = RegolithInitRunner(
        callbacks = kimchiRegolithAdapter,
        initializers = listOf(
            GlassClientModule.createInitializer(),
            daemonInitializer
        ),
    )
    val initRunner: InitRunner = regolith

    private val httpClient = HttpClient(OkHttp) {}
    private val actionClient = ActionClient(httpClient, logger)

    val layoutFactory = UiLayoutFactory(
        actionClient = actionClient,
        json = Json
    )

    val ipProvider = IpProvider(
        connectivityManager = application.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager,
        context = application
    )
}
