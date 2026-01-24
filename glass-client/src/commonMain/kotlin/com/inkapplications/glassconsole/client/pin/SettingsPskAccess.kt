package com.inkapplications.glassconsole.client.pin

import com.inkapplications.data.transformer.DataTransformer
import com.inkapplications.glassconsole.structures.pin.Psk
import kotlinx.coroutines.flow.Flow
import regolith.data.settings.*
import regolith.data.settings.structure.StringData

/**
 * Provides PSK values using a Regolith Settings database.
 */
internal class SettingsPskAccess(
    private val settingsAccess: SettingsAccess,
): PskAccess {
    private val pskSetting = StringData(
        key = "glass.psk",
        name = "PSK",
        dataTransformer = object : DataTransformer<String?, Psk?> {
            override fun reverseTransform(data: Psk?): String? = data?.value
            override fun transform(data: String?): Psk? = data?.let(::Psk)
        },
        defaultValue = null,
        level = SettingLevel.Hidden,
    )
    override val psk: Flow<Psk?> = settingsAccess.observeSetting(pskSetting)

    override suspend fun setPsk(psk: Psk) {
        return settingsAccess.writeSetting(pskSetting, psk)
    }
}
