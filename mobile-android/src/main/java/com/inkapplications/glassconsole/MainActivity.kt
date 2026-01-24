package com.inkapplications.glassconsole

import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
import android.view.WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_OFF
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.inkapplications.glassconsole.ApplicationModule.Config.DISPLAY_SERVER_PORT
import com.inkapplications.glassconsole.structures.BacklightConfig
import ink.ui.render.compose.bindAndPresent
import ink.ui.render.compose.theme.ColorVariant
import ink.ui.render.compose.theme.darken
import ink.ui.render.compose.theme.defaultTheme
import ink.ui.structures.render.onUpdate
import inkapplications.spondee.structure.toFloat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.seconds

/**
 * Main screen of the application, shows the display as configured after
 * start-up.
 */
class MainActivity : ComponentActivity() {
    private val viewModel: ConfigDisplayModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        setContent {
            val state = viewModel.state.collectAsState()
            val backlightConfig = (state.value as? ScreenState.Configured)?.config?.backlight
            val overrideBacklight = remember { mutableStateOf(false) }
            val scope = rememberCoroutineScope()
            val theme = defaultTheme().copy(
                colors = ColorVariant.Defaults.dark.copy(
                    background = Color.Black,
                    surface = ColorVariant.Defaults.dark.surface.darken(.08f),
                    surfaceInteraction = ColorVariant.Defaults.dark.surfaceInteraction.darken(.05f),
                )
            )
            val layoutValid = remember { mutableStateOf(false) }
            val timerJob = remember { mutableStateOf<kotlinx.coroutines.Job?>(null) }

            window.attributes = window.attributes.apply {
                screenBrightness = if (overrideBacklight.value && isDimmed(backlightConfig)) BRIGHTNESS_OVERRIDE_NONE
                    else when (backlightConfig) {
                        null, BacklightConfig.Auto -> BRIGHTNESS_OVERRIDE_NONE
                        is BacklightConfig.Fixed -> backlightConfig.brightness.toDecimal().toFloat()
                        is BacklightConfig.Off -> BRIGHTNESS_OVERRIDE_OFF
                    }
            }

            DisplayApplication.module.run {
                val server = remember {
                    remoteRenderModule.createServer(
                        presenter = serverUiPresenter.onUpdate {
                            timerJob.value?.cancel()
                            layoutValid.value = true

                            val expiration = (state.value as? ScreenState.Configured)?.config?.expiration
                            if (expiration != null) {
                                timerJob.value = scope.launch {
                                    delay(expiration)
                                    layoutValid.value = false
                                }
                            }
                        },
                        port = DISPLAY_SERVER_PORT,
                    )
                }
                LaunchedEffect("DisplayServer") {
                    withContext(Dispatchers.IO) {
                        server.bind()
                    }
                }
                Box(
                    modifier = Modifier.fillMaxSize().pointerInput(backlightConfig) {
                        detectTapGestures {
                            val tapToWake = when (backlightConfig) {
                                null, BacklightConfig.Auto -> false
                                is BacklightConfig.Fixed -> backlightConfig.tapToWake
                                is BacklightConfig.Off -> backlightConfig.tapToWake
                            }
                            if (tapToWake && isDimmed(backlightConfig)) {
                                overrideBacklight.value = true
                                scope.launch {
                                    delay(10.seconds)
                                    overrideBacklight.value = false
                                }
                            }
                        }
                    }
                ) {
                    if (layoutValid.value) {
                        serverUiPresenter.bind(theme)
                    } else {
                        localUiPresenter.bindAndPresent(theme, layoutFactory.forState(state.value))
                    }
                }
            }
        }
    }

    private fun isDimmed(screenConfig: BacklightConfig?): Boolean {
        val setting = try {
            Settings.System.getInt(application.contentResolver, Settings.System.SCREEN_BRIGHTNESS).toFloat() / 255f
        } catch (e: Settings.SettingNotFoundException) {
            1f
        }
        return when (screenConfig) {
            null, BacklightConfig.Auto -> false
            is BacklightConfig.Off -> true
            is BacklightConfig.Fixed -> screenConfig.brightness.toDecimal().toFloat() < setting
        }
    }
}
