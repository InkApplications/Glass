package com.inkapplications.glassconsole.renderer

import android.media.AudioManager
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.inkapplications.glassconsole.android.playNotificationSound
import com.inkapplications.glassconsole.client.pin.PinValidator
import com.inkapplications.glassconsole.client.pin.PskAccess
import com.inkapplications.glassconsole.elements.HashingPinPadElement
import com.inkapplications.glassconsole.elements.PinPadElement
import com.inkapplications.glassconsole.sound
import com.inkapplications.glassconsole.structures.pin.Pin
import ink.ui.render.compose.renderer.ElementRenderer
import ink.ui.render.compose.renderer.RenderResult
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.Sentiment
import ink.ui.structures.elements.ThrobberElement
import ink.ui.structures.elements.UiElement
import kotlinx.coroutines.launch

class HashingPinPadRenderer(
    private val pinValidator: PinValidator,
    private val pskAccess: PskAccess,
): ElementRenderer {
    @Composable
    override fun render(element: UiElement, theme: ComposeRenderTheme, parent: ElementRenderer): RenderResult {
        if (element !is HashingPinPadElement) return RenderResult.NotRendered

        val soundScope = rememberCoroutineScope()
        val context = LocalContext.current
        val pinState = remember { mutableStateOf("") }
        val pskState = pskAccess.psk.collectAsState(null)
        val psk = pskState.value

        if (psk == null) {
            parent.render(
                element = ThrobberElement(
                    caption = "Loading PSK",
                ),
                theme = theme,
                parent = parent
            )
            return RenderResult.Rendered
        }
        parent.render(
            PinPadElement(
                value = pinState.value,
                onKeyPress = { pinState.value += it },
                onEnter = {
                    val validation = runCatching {
                        pinValidator.validate(
                            psk = psk,
                            witness = element.witness,
                            pin = pinState.value.let(::Pin),
                            challengeNonce = element.challengeNonce,
                        )
                    }
                    validation.onSuccess {
                        pinState.value = ""
                        soundScope.launch {
                            context.playNotificationSound(Sentiment.Positive.sound)
                        }
                        element.onSuccess(it)
                    }
                    validation.onFailure {
                        soundScope.launch {
                            context.playNotificationSound(Sentiment.Negative.sound)
                        }
                        pinState.value = ""
                    }
                },
                onClear = { pinState.value = "" },
            ),
            theme,
            parent
        )

        return RenderResult.Rendered
    }
}

