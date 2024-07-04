package com.inkapplications.glassconsole

import android.app.Application
import android.speech.tts.TextToSpeech
import com.inkapplications.glassconsole.android.playNotificationSound
import com.inkapplications.glassconsole.server.DisplayServer
import com.inkapplications.glassconsole.structures.Broadcast
import kimchi.logger.KimchiLogger
import kotlinx.coroutines.flow.collectLatest
import regolith.processes.daemon.Daemon
import java.util.Locale

class Broadcaster(
    private val displayServer: DisplayServer,
    private val application: Application,
    private val logger: KimchiLogger,
): TextToSpeech.OnInitListener, Daemon {
    private lateinit var tts: TextToSpeech
    private var ttsAvailable = false
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                logger.error("TTS Init Failed: This Language is not supported")
            } else {
                ttsAvailable = true
                logger.trace("TTS Init complete")
            }
        } else {
            logger.error("TTS Init Failed with code: $status")
        }
    }

    override suspend fun startDaemon(): Nothing {
        tts = TextToSpeech(application, this)
        displayServer.broadcasts.collectLatest(::onBroadcast)
        throw IllegalStateException("Broadcasts completed unexpectedly.")
    }

    private fun onAnnouncement(announcement: Broadcast.Announcement) {
        if (!ttsAvailable) return run {
            logger.warn("TTS Unavailable. Cannot handle announcement")
        }
        tts.speak(announcement.text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private suspend fun onBroadcast(broadcast: Broadcast) {
        when (broadcast) {
            is Broadcast.Ping -> application.playNotificationSound(broadcast.sentiment.sound)
            is Broadcast.Announcement -> onAnnouncement(broadcast)
        }
    }
}
