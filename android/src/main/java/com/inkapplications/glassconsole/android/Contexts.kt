package com.inkapplications.glassconsole.android

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.annotation.RawRes
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Play a notification sound by resource ID.
 */
suspend fun Context.playNotificationSound(@RawRes resourceId: Int) {
    suspendCoroutine { continuation ->
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val mediaPlayer = MediaPlayer.create(
            this,
            resourceId,
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build(),
            audioManager.generateAudioSessionId(),
        )
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
        }
        mediaPlayer.setOnCompletionListener { mp ->
            mp.release()
            continuation.resume(Unit)
        }
    }
}
