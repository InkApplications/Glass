package com.inkapplications.glassconsole

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.inkapplications.glassconsole.structures.Button
import io.ktor.client.request.get
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
        setContent {
            val screenState = viewModel.state.collectAsState().value

            Box(
                modifier = Modifier.displayCutoutPadding()
            ) {
                when (screenState) {
                    is ScreenState.Configured -> DisplayScreen(screenState.config, ::onButtonClick)
                    ScreenState.NoData -> EmptyScreen()
                }
            }
        }
    }

    private fun onButtonClick(item: Button) {
        lifecycleScope.launch(Dispatchers.IO) {
            ApplicationModule.httpClient.get(item.url)
        }
    }
}
