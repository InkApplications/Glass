package com.inkapplications.glassconsole

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*

class DisplayViewModel: ViewModel() {
    private val latestConfig = DisplayApplication.module.displayServer.config
        .onStart { emit(null) }
        .flatMapLatest { config ->
            when (val expiration = config?.expiration) {
                null -> flowOf(config)
                else -> flow {
                    emit(config)
                    kotlinx.coroutines.delay(expiration)
                    emit(null)
                }
            }
        }

    val state = combine(
        latestConfig,
        DisplayApplication.module.ipProvider.currentIps,
    ) { config, ips ->
        when {
            config != null -> ScreenState.Configured(config, ips.isNotEmpty())
            ips.isNotEmpty() -> ScreenState.NoData(ips.joinToString(", "))
            else -> ScreenState.NoConnection
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ScreenState.Initial)
}
