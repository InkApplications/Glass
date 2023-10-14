package com.inkapplications.glassconsole

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DisplayViewModel: ViewModel() {
    val state = ApplicationModule.displayServer.config
        .map { it?.let { ScreenState.Configured(it) } ?: ScreenState.NoData }
        .stateIn(viewModelScope, SharingStarted.Eagerly, ScreenState.NoData)
}
