package com.inkapplications.glassconsole

import android.os.Bundle
import android.view.WindowManager
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
import com.inkapplications.glassconsole.structures.ButtonItem
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
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        setContent {
            val screenState = viewModel.state.collectAsState().value

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(InkTheme.color.background)
                    .displayCutoutPadding()
            ) {
                when (screenState) {
                    is ScreenState.Initial -> InitialScreen()
                    is ScreenState.NoConnection -> Disconnected()
                    is ScreenState.NoData -> AwaitingConfig(screenState.ips)
                    is ScreenState.Configured -> DisplayScreen(screenState.config, screenState.connected, ::onButtonClick)
                }
            }
        }
    }

    private fun onButtonClick(item: ButtonItem) {
        lifecycleScope.launch(Dispatchers.IO) {
            DisplayApplication.module.actionClient.sendAction(item.action)
        }
    }
}
