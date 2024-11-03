package com.omerakkoyun.exoplayersample

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Omer AKKOYUN on 3.11.2024.
 */
@HiltAndroidApp
class HiltApplication : Application() {
    companion object{
        private lateinit var instance : HiltApplication
        fun getAppContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }
}
