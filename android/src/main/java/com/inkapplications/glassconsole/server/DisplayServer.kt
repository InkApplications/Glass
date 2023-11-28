package com.inkapplications.glassconsole.server

import com.inkapplications.glassconsole.structures.Broadcast
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
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.routing
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.serialization.json.Json
import regolith.processes.daemon.Daemon

/**
 * HTTP Server listening for display instructions.
 */
class DisplayServer: Daemon {
    private val currentConfig = MutableSharedFlow<DisplayConfig?>()
    private val mutableBroadcasts = MutableSharedFlow<Broadcast>()

    /**
     * The current configuration settings for the display.
     */
    val config: SharedFlow<DisplayConfig?> = currentConfig

    /**
     * Broadcasts sent to the display.
     */
    val broadcasts: SharedFlow<Broadcast> = mutableBroadcasts

    override suspend fun startDaemon(): Nothing {
        embeddedServer(CIO, port = 8080) {
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
                    currentConfig.emit(config)
                    call.respond(HttpStatusCode.NoContent)
                }
                post("/broadcast") {
                    val broadcast = try {
                        call.receive<Broadcast>()
                    } catch (e: Exception) {
                        android.util.Log.e("Server", "Error Receiving Request", e)
                        throw e
                    }

                    mutableBroadcasts.emit(broadcast)
                    call.respond(HttpStatusCode.NoContent)
                }
            }
        }.start(wait = true)
        throw IllegalStateException("Server stopped unexpectedly")
    }
}
