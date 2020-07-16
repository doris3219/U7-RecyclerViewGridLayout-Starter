package com.example.android.dessertclicker

import android.app.Application
import timber.log.Timber

class ClickerApplication : Application(){       //在Application必要時導入該類
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())      //初始化Timber
    }
}
