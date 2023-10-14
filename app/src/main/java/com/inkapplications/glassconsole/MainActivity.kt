package com.inkapplications.glassconsole

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
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
        lifecycleScope.launch(Dispatchers.IO) {
            ApplicationModule.displayServer.start()
        }
        setContent {
            val screenState = viewModel.state.collectAsState().value

            when (screenState) {
                is ScreenState.Configured -> DisplayScreen(screenState.config)
                ScreenState.NoData -> EmptyScreen()
            }
        }
    }
}
