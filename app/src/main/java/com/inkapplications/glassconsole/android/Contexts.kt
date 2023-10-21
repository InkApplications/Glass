package com.inkapplications.glassconsole.android

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.annotation.RawRes

/**
 * Play a notification sound by resource ID.
 */
fun Context.playNotificationSound(@RawRes resourceId: Int) {
    val mediaPlayer = MediaPlayer.create(this, resourceId)
    mediaPlayer.setAudioAttributes(
        AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
    )
    mediaPlayer.start()

    mediaPlayer.setOnCompletionListener { mp ->
        mp.release()
    }
}
