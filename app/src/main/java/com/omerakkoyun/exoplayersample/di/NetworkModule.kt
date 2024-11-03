package com.omerakkoyun.exoplayersample.di

import com.omerakkoyun.exoplayersample.HiltApplication
import com.omerakkoyun.exoplayersample.common.AuthInterceptor
import com.omerakkoyun.exoplayersample.common.Constants.BASE_URL
import com.omerakkoyun.exoplayersample.common.SecurePreferences
import com.omerakkoyun.exoplayersample.data.service.MovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Omer AKKOYUN on 3.11.2024.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {



    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val securePreferences = SecurePreferences(HiltApplication.getAppContext())
        return OkHttpClient.Builder()
            //.addInterceptor(LoggingInterceptor()) // Logging Interceptor
            .addInterceptor(AuthInterceptor(securePreferences.getAuthToken() ?: ""))
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // OkHttp istemcisi ekleniyor
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieApiService(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }
}