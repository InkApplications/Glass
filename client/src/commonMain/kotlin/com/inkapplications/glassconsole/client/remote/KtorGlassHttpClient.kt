package com.inkapplications.glassconsole.client.remote

import com.inkapplications.glassconsole.client.HttpException
import com.inkapplications.glassconsole.structures.Broadcast
import com.inkapplications.glassconsole.structures.DisplayConfig
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * Uses Ktor to send HTTP requests to a Glass display.
 */
internal class KtorGlassHttpClient: GlassHttpClient {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    override suspend fun setConfig(
        config: DisplayConfig,
        host: String,
        port: Int,
    ) {
        val response = httpClient.put {
            url.host = host
            url.path("config")
            url.protocol = URLProtocol.HTTP
            url.port = port
            contentType(ContentType.Application.Json)
            setBody(config)
        }

        if (response.status.isSuccess()) return

        throw HttpException(response.status.value, response.bodyAsText())
    }

    override suspend fun broadcast(
        broadcast: Broadcast,
        host: String,
        port: Int,
    ) {
        val response = httpClient.post {
            url.host = host
            url.path("broadcast")
            url.protocol = URLProtocol.HTTP
            url.port = port
            contentType(ContentType.Application.Json)
            setBody(broadcast)
        }

        if (response.status.isSuccess()) return

        throw HttpException(response.status.value, response.bodyAsText())
    }
}
