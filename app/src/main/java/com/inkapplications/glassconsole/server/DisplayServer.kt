package com.inkapplications.glassconsole.server

import com.inkapplications.glassconsole.structures.DisplayConfig
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.put
import io.ktor.server.routing.routing
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.Json

/**
 * HTTP Server listening for display instructions.
 */
class DisplayServer {
    private val currentConfig: MutableStateFlow<DisplayConfig?> = MutableStateFlow(null)

    /**
     * The current configuration settings for the display.
     */
    val config: StateFlow<DisplayConfig?> = currentConfig

    /**
     * Start the HTTP server to listen for display requests.
     *
     * @param port The http port to listen on.
     */
    suspend fun start(port: Int = 8080) {
        embeddedServer(CIO, port = port) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
            routing {
                put("/update") {
                    val config = try {
                        call.receive<DisplayConfig>()
                    } catch (e: Exception) {
                        android.util.Log.e("Server", "Error Receiving Request", e)
                        throw e
                    }
                    currentConfig.value = config
                    call.respond(HttpStatusCode.NoContent)
                }
            }
        }.start(wait = true)
    }
}
