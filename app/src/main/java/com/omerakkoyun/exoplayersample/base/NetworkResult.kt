package com.omerakkoyun.exoplayersample.base

/**
 * Created by Omer AKKOYUN on 3.11.2024.
 */

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null,
    val networkError: Boolean = false) {


    class Success<T>(data: T) : NetworkResult<T>(data = data, message = "", networkError = false)
    class Error<T>(networkError: Boolean, message: String?) : NetworkResult<T>(data = null, message = message, networkError = networkError)
    class Loading<T> : NetworkResult<T>()
}