package com.inkapplications

import android.app.Application
import com.inkapplications.glassconsole.ApplicationModule

class DisplayApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ApplicationModule.application = this
    }
}
