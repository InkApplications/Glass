package com.inkapplications.glassconsole

import com.inkapplications.glassconsole.ApplicationModule.Config.CONFIG_SERVER_PORT
import com.inkapplications.glassconsole.client.GlassClientModule
import com.inkapplications.glassconsole.renderer.HashingPinPadRenderer
import com.inkapplications.glassconsole.renderer.PinPadRenderer
import com.inkapplications.glassconsole.server.ConfigServer
import com.inkapplications.glassconsole.server.IpProvider
import com.inkapplications.glassconsole.structures.pin.EnterHashedPinEvent
import com.inkapplications.glassconsole.structures.pin.HashingPinPadElement
import com.inkapplications.glassconsole.structures.pin.HashingPinPadElementSerializer
import ink.ui.render.compose.ComposePresenter
import ink.ui.render.remote.RemoteRenderModule
import kimchi.Kimchi
import kimchi.logger.LogLevel
import kimchi.logger.defaultWriter
import kimchi.logger.withThreshold
import kotlinx.datetime.Clock
import regolith.data.settings.AndroidSettingsModule
import regolith.init.InitRunner
import regolith.init.RegolithInitRunner
import regolith.processes.daemon.DaemonInitializer

/**
 * Object graph used application-wide.
 */
class ApplicationModule(
    application: DisplayApplication,
) {
    object Config
    {
        val DISPLAY_SERVER_PORT = 8081
        val CONFIG_SERVER_PORT = 8082
    }
    private val clock = Clock.System
    private val logger = Kimchi.apply {
        addLog(
            if (BuildConfig.DEBUG) defaultWriter
            else defaultWriter.withThreshold(LogLevel.INFO)
        )
    }
    val configServer = ConfigServer(logger, CONFIG_SERVER_PORT)
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

    private val broadcaster = Broadcaster(configServer, application, logger)
    private val daemonInitializer = DaemonInitializer(
        callbacks = kimchiRegolithAdapter,
        daemons = listOf(configServer, broadcaster),
    )
    private val regolith = RegolithInitRunner(
        callbacks = kimchiRegolithAdapter,
        initializers = listOf(
            GlassClientModule.createInitializer(),
            daemonInitializer
        ),
    )
    val initRunner: InitRunner = regolith
    val remoteRenderModule = RemoteRenderModule(
        serializationConfig = {
            addElementSerializer(HashingPinPadElement::class, HashingPinPadElementSerializer(uiEvents))
            addEventSerializer(EnterHashedPinEvent::class, EnterHashedPinEvent.serializer(), EnterHashedPinEvent.Listeners.PinListener)
        }
    )
    val serverUiPresenter = ComposePresenter(
        renderers = DisplayApplication.module.renderers,
    )
    val localUiPresenter = ComposePresenter(
        renderers = DisplayApplication.module.renderers,
    )

    val layoutFactory = UiLayoutFactory()

    val ipProvider = IpProvider(
        connectivityManager = application.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager,
        context = application
    )
}
