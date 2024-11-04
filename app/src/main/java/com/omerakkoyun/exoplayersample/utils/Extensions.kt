package com.omerakkoyun.exoplayersample.utils

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.omerakkoyun.exoplayersample.R
import com.omerakkoyun.exoplayersample.data.local.MovieEntity
import com.omerakkoyun.exoplayersample.data.remote.MovieResponse
import com.omerakkoyun.exoplayersample.data.remote.ResultItem
import com.omerakkoyun.exoplayersample.enums.MovieRequestType


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

private fun createPlaceHolder(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 12f
        centerRadius = 40f
        start()
    }
}

fun ImageView.loadImageFromID(URL: String?) {
    val defaultImage: Int = R.drawable.movie_logo // Varsayılan bir resim
    val placeholder = createPlaceHolder(this.context)
    this.load(URL) {
        crossfade(true)
        crossfade(500)
        placeholder(placeholder)
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

fun Context.isTabletDevice(): Boolean{
    return resources.configuration.smallestScreenWidthDp >= 600
}

fun ResultItem.toMovieEntity(movieType: MovieRequestType, page: Int, totalPages: Int, totalResults: Int): MovieEntity {
    return MovieEntity(
        id = this.id ?:0,
        title = this.title ?:"",
        overview = this.overview?:"",
        releaseDate = this.releaseDate?:"",
        posterPath = this.posterPath?:"",
        voteAverage = this.voteAverage?: 0.0,
        movieType = movieType,
        page = page,
        totalPages = totalPages,
        totalResults = totalResults,
    )
}

fun MovieEntity.toResultItem(): ResultItem {
    return ResultItem(
        adult = null, // MovieEntity'de adult bilgisi yoksa null veriyoruz
        backdropPath = this.posterPath, // Örneğin posterPath'ı backdropPath olarak kullanabiliriz
        genreIds = listOf(), // Genre ID'leri yoksa boş liste döndürülüyor
        id = this.id,
        originalLanguage = null, // MovieEntity'de bu alan yoksa null veriyoruz
        originalTitle = this.title, // MovieEntity'den title kullanıldı
        overview = this.overview,
        popularity = null, // MovieEntity'de popularity bilgisi yoksa null veriyoruz
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        video = false, // MovieEntity'de video bilgisi yoksa false
        voteAverage = this.voteAverage,
        voteCount = 0, // MovieEntity'de voteCount bilgisi yoksa 0
        movieRequestType = this.movieType
    )
}

fun Context.isInternetAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo
        networkInfo != null && networkInfo.isConnected
    }
}