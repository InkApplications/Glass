package com.inkapplications.glassconsole

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.inkapplications.glassconsole.client.ActionClient
import com.inkapplications.glassconsole.server.DisplayServer
import com.inkapplications.glassconsole.server.IpProvider
import ink.ui.render.compose.ComposeRenderer
import ink.ui.render.compose.theme.ColorVariant
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.render.compose.theme.TypographyVariant
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

    val layoutFactory = UiLayoutFactory(
        actionClient = actionClient,
    )

    val renderer = ComposeRenderer(
        theme = ComposeRenderTheme(
            colors = ColorVariant.Defaults.dark.copy(
                background = Color.Black,
            ),
            typography = TypographyVariant().withFontFamily(
                FontFamily(
                    Font(R.font.roboto_mono_light, FontWeight.Normal),
                    Font(R.font.roboto_mono_medium, FontWeight.Bold),
                )
            )
        )
    )

    val ipProvider = IpProvider(
        connectivityManager = application.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager,
        context = application
    )
}
