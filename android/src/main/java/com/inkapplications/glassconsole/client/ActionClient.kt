package com.inkapplications.glassconsole.client

import com.inkapplications.glassconsole.structures.Action
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import kotlinx.coroutines.CancellationException

/**
 * Executes actions made by various elements on the display.
 */
class ActionClient(
    private val httpClient: HttpClient,
) {
    suspend fun sendAction(
        action: Action,
    ) {
        try {
            when (action) {
                is Action.Get -> httpClient.get(action.url)
                is Action.Put -> httpClient.put(action.url) {
                    setBody(action.body)
                }

                is Action.Post -> httpClient.post(action.url) {
                    setBody(action.body)
                }

                is Action.Delete -> httpClient.delete(action.url)
            }
        } catch (e: CancellationException) {
            android.util.Log.w("ActionClient", "Action cancelled", e)
            throw e
        } catch (e: Exception) {
            android.util.Log.e("ActionClient", "Error sending action", e)
        }
    }
}
