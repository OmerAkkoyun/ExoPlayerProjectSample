package com.omerakkoyun.exoplayersample

import android.app.Application
import android.content.Context
import com.omerakkoyun.exoplayersample.common.SecurePreferences
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
        saveAuthToken(this)
    }

    private fun saveAuthToken(context: Context) {
        val securePrefs = SecurePreferences(context)
        // inceleyen icin not: orijinal token dinamik gelmeli, buradaki hardcoded token sadece ornektir. dinamik gelen yerde securePrefs kullanılmalı
        val testToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkMjAxOWZhNzMzY2IzNDhlMWU0YWMzNzQ4Zjc5YmFmYyIsIm5iZiI6MTczMDQ5MTIzNS4wMzM1OTUsInN1YiI6IjYyM2YxNDg0MWNmZTNhMDA4NzY2ZGEyZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.wRq6S7TEBj_Qb0v63pNY8xoPwzmkswZQrnM4ah9lzB0"

        val savedToken = securePrefs.getAuthToken()
        if (savedToken == null) {
            securePrefs.saveAuthToken(testToken)
        }
    }
}
