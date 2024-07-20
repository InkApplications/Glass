package com.inkapplications.glassconsole.client

import com.inkapplications.glassconsole.structures.Action
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import kimchi.logger.KimchiLogger
import kotlinx.coroutines.CancellationException

/**
 * Executes actions made by various elements on the display.
 */
class ActionClient(
    private val httpClient: HttpClient,
    private val logger: KimchiLogger,
) {
    suspend fun sendAction(
        action: Action,
    ) {
        try {
            when (action) {
                is Action.Get -> httpClient.get(action.url) {
                    logger.debug("GET: ${action.url}")
                }
                is Action.Put -> httpClient.put(action.url) {
                    logger.debug("PUT: ${action.url}")
                    logger.debug(action.body)
                    setBody(action.body)
                }

                is Action.Post -> httpClient.post(action.url) {
                    logger.debug("POST: ${action.url}")
                    logger.debug(action.body)
                    setBody(action.body)
                }

                is Action.Delete -> {
                    logger.debug("DELETE: ${action.url}")
                    httpClient.delete(action.url)
                }
            }
        } catch (e: CancellationException) {
            logger.warn("Action cancelled", e)
            throw e
        } catch (e: Exception) {
            logger.error("Error sending action", e)
        }
    }
}
