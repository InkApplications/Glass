package com.inkapplications.glassconsole

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.inkapplications.glassconsole.android.playNotificationSound
import com.inkapplications.glassconsole.structures.Broadcast
import com.inkapplications.glassconsole.structures.ButtonItem
import com.inkapplications.glassconsole.structures.Indicator
import com.inkapplications.glassconsole.ui.sound
import com.inkapplications.glassconsole.ui.theme.InkTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

/**
 * Main screen of the application, shows the display as configured after
 * start-up.
 */
class MainActivity : ComponentActivity(), TextToSpeech.OnInitListener {
    private val viewModel: DisplayViewModel by viewModels()
    private lateinit var textToSpeech: TextToSpeech
    private var ttsInitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textToSpeech = TextToSpeech(this, this)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        lifecycleScope.launch(Dispatchers.IO) {
            ApplicationModule.displayServer.start()
        }
        lifecycleScope.launch {
            ApplicationModule.displayServer.broadcasts.collect { broadcast ->
                onBroadcast(broadcast)
            }
        }
        setContent {
            val screenState = viewModel.state.collectAsState().value

            Box(
                modifier = Modifier.fillMaxSize()
                    .background(InkTheme.color.background)
                    .displayCutoutPadding()
            ) {
                when (screenState) {
                    is ScreenState.Configured -> DisplayScreen(screenState.config, screenState.connected, ::onButtonClick)
                    ScreenState.Initial -> InitialScreen()
                    ScreenState.NoConnection -> Disconnected()
                    is ScreenState.NoData -> AwaitingConfig(screenState.ips)
                }
            }
        }
    }

    private fun onButtonClick(item: ButtonItem) {
        lifecycleScope.launch(Dispatchers.IO) {
            ApplicationModule.actionClient.sendAction(item.action)
        }
    }

    private suspend fun onBroadcast(broadcast: Broadcast) {
        when (broadcast) {
            is Broadcast.Ping -> playNotificationSound(broadcast.indicator.sound)
            is Broadcast.Announcement -> {
                broadcast.indicator?.sound?.run { playNotificationSound(this) }
                if (ttsInitialized) {
                    textToSpeech.speak(broadcast.text, TextToSpeech.QUEUE_FLUSH, null, null)
                }
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported")
            } else {
                ttsInitialized = true
            }
        } else {
            Log.e("TTS", "Initilization Failed")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.shutdown()
    }
}
