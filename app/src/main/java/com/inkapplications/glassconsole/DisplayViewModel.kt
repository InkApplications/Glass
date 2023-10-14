package com.inkapplications.glassconsole

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class DisplayViewModel: ViewModel() {
    val state = combine(
        ApplicationModule.displayServer.config,
        ApplicationModule.ipProvider.currentIps,
    ) { config, ips ->
        when {
            config != null -> ScreenState.Configured(config, ips.isNotEmpty())
            ips.isNotEmpty() -> ScreenState.NoData(ips.joinToString(", "))
            else -> ScreenState.NoConnection
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ScreenState.Initial)
}
