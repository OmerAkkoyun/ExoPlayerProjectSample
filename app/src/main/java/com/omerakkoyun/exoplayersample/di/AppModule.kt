package com.omerakkoyun.exoplayersample.di

import android.content.Context
import com.omerakkoyun.exoplayersample.data.local.MovieDao
import com.omerakkoyun.exoplayersample.data.local.MovieDatabase
import com.omerakkoyun.exoplayersample.data.repository.MovieRepositoryImpl
import com.omerakkoyun.exoplayersample.data.service.MovieApiService
import com.omerakkoyun.exoplayersample.domain.repository.MovieRepository
import com.omerakkoyun.exoplayersample.domain.usecase.MoviesByReleaseDateUseCase
import com.omerakkoyun.exoplayersample.domain.usecase.PopularMoviesUseCase
import com.omerakkoyun.exoplayersample.domain.usecase.TopRevenuesMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Omer AKKOYUN on 3.11.2024.
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieRepository(movieApiService: MovieApiService,movieDao: MovieDao): MovieRepository {
        return MovieRepositoryImpl(movieApiService,movieDao)
    }

    @Provides
    @Singleton
    fun provideGetUpcomingMoviesUseCase(movieRepository: MovieRepository): TopRevenuesMoviesUseCase {
        return TopRevenuesMoviesUseCase(movieRepository)
    }

    @Provides
    @Singleton
    fun providePopularMoviesUseCase(movieRepository: MovieRepository): PopularMoviesUseCase {
        return PopularMoviesUseCase(movieRepository)
    }

    @Provides
    @Singleton
    fun provideNowPlayingMoviesUseCase(movieRepository: MovieRepository): MoviesByReleaseDateUseCase {
        return MoviesByReleaseDateUseCase(movieRepository)
    }

    // room
    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return MovieDatabase.getInstance(context)
    }

    @Provides
    fun provideMovieDao(movieDao: MovieDatabase) : MovieDao {
        return movieDao.getMovieDao()
    }


}