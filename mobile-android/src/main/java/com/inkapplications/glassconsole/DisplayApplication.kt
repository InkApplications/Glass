package com.inkapplications.glassconsole

import android.app.Application

class DisplayApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        module = ApplicationModule(this)
        module.initRunner.initialize()
    }

    companion object {
        lateinit var module: ApplicationModule
    }
}
