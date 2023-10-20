package com.inkapplications.glassconsole.client

import com.inkapplications.glassconsole.structures.DisplayConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * HTTP Client for sending display instructions.
 */
class GlassClient {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    /**
     * Send a new display configuration to the server.
     *
     * @param config The new configuration data to send.
     * @param host The IP or hostname of the device to be configured
     * @param port The port that the display server is running on for the display device (default: 8080)
     */
    suspend fun updateDisplay(
        config: DisplayConfig,
        host: String,
        port: Int = 8080,
    ) {
        httpClient.put {
            url.host = host
            url.protocol = URLProtocol.HTTPS
            url.port = port
            contentType(ContentType.Application.Json)
            setBody(config)
        }
    }
}
