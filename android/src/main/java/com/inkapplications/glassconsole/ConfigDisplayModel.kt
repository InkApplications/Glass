package com.inkapplications.glassconsole

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inkapplications.glassconsole.DisplayApplication.Companion.module
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ConfigDisplayModel: ViewModel() {
    val state = combine(
        module.configServer.config.onStart { emit(null) },
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
            config != null -> ScreenState.Configured(config, ips)
            ips.isNotEmpty() -> ScreenState.NoData(ips)
            else -> ScreenState.NoConnection
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ScreenState.Initial)
}
