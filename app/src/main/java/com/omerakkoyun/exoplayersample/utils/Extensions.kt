package com.omerakkoyun.exoplayersample.utils

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.ImageView
import coil.load
import com.omerakkoyun.exoplayersample.R


/**
 * Created by Omer AKKOYUN on 3.11.2024.
 */

fun View.makeItVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeItGone() {
    this.visibility = View.GONE
}


fun View.setVisible(visible: Boolean) {
    if (visible) {
        this.makeItVisible()
    } else {
        this.makeItGone()
    }
}


fun ImageView.loadImageFromID(URL: String?) {
    val defaultImage: Int = R.drawable.movie_logo // Varsayılan bir resim
    this.load(URL) {
        crossfade(true)
        crossfade(500)
        error(defaultImage)
    }
}

fun Activity.isLandscape(): Boolean {
    return this.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            || (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)

}

fun Activity.hideSystemBars() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        this.window?.insetsController?.apply {
            hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    } else {
        // before Android 11
        @Suppress("DEPRECATION")
        this.window?.decorView?.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
    }
}
