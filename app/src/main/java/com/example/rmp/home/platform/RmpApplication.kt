package com.example.rmp.home.platform

import android.app.Application
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.rmp.platform.ui.charactersModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class RmpApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        run()

    }

    private fun run() {
        startKoin {
            androidContext(this@RmpApplication)
        }
        loadKoinModules(charactersModule)
    }
}