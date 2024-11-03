package com.omerakkoyun.exoplayersample.common

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Omer AKKOYUN on 3.11.2024.
 */
class AuthInterceptor(private val authToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", authToken)
            .build()
        return chain.proceed(newRequest)
    }
}