package com.inkapplications.glassconsole

import android.os.Bundle
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
import com.inkapplications.glassconsole.ui.theme.InkTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Main screen of the application, shows the display as configured after
 * start-up.
 */
class MainActivity : ComponentActivity() {
    private val viewModel: DisplayViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    private fun onBroadcast(broadcast: Broadcast) {
        when (broadcast) {
            is Broadcast.Ping -> {
                when (broadcast.indicator) {
                    Indicator.Nominal -> playNotificationSound(R.raw.nominal)
                    Indicator.Primary -> playNotificationSound(R.raw.primary)
                    Indicator.Positive -> playNotificationSound(R.raw.positive)
                    Indicator.Danger -> playNotificationSound(R.raw.danger)
                    Indicator.Negative -> playNotificationSound(R.raw.negative)
                    Indicator.Idle -> playNotificationSound(R.raw.idle)
                }
            }
        }
    }
}
