package com.inkapplications.glassconsole.client

import com.inkapplications.glassconsole.client.pin.*
import com.inkapplications.glassconsole.client.pin.Sha256PinValidator
import com.inkapplications.glassconsole.client.remote.GlassHttpClient
import com.inkapplications.glassconsole.client.remote.KtorGlassHttpClient
import kotlinx.datetime.Clock
import regolith.data.settings.SettingsAccess
import regolith.init.Initializer

/**
 * Module to provide access to create client components.
 */
object GlassClientModule {
    /**
     * Create an initializer to be run at application startup.
     */
    fun createInitializer(): Initializer {
        return LibSodiumInitializer
    }

    /**
     * Create a HTTP client for making requests to the Glass Display.
     */
    fun createHttpClient(): GlassHttpClient {
        return KtorGlassHttpClient()
    }

    /**
     * Create a PIN validator service for validating PIN codes.
     *
     * @param clock The Clock to use for timestamps on challenge responses.
     */
    fun createPinValidator(
        clock: Clock = Clock.System
    ): PinValidator {
        return Sha256PinValidator(clock)
    }

    /**
     * Create a service for getting/saving a PSK to a database.
     */
    fun createPskAccess(
        settingsAccess: SettingsAccess
    ): PskAccess {
        return SettingsPskAccess(settingsAccess)
    }

    /**
     * Create a service for generating random PSKs.
     */
    fun createPskGenerator(): PskGenerator {
        return ChunkedHexPskGenerator()
    }

    /**
     * Create a service for generating secure random nonce values.
     */
    fun createNonceGenerator(): NonceGenerator {
        return RandomHexNonceGenerator()
    }
}
