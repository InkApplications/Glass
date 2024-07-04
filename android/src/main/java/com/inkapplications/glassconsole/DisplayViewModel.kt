package com.inkapplications.glassconsole

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inkapplications.glassconsole.DisplayApplication.Companion.module
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DisplayViewModel: ViewModel() {
    private val latestConfig = module.displayServer.config
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
        module.ipProvider.currentIps,
        module.pskAccess.psk,
        flowOf(module.pskGenerator.generate())
    ) { config, ips, psk, newPsk ->
        when {
            psk == null -> {
                ScreenState.ShowPsk(
                    psk = newPsk,
                    onDismiss = {
                        viewModelScope.launch {
                            module.pskAccess.setPsk(newPsk)
                        }
                    }
                )
            }
            config != null -> ScreenState.Configured(config, ips.isNotEmpty())
            ips.isNotEmpty() -> ScreenState.NoData(ips.joinToString(", "))
            else -> ScreenState.NoConnection
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ScreenState.Initial)
}
