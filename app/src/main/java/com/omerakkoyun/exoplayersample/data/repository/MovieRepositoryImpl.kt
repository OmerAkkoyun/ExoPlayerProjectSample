package com.omerakkoyun.exoplayersample.data.repository

import com.omerakkoyun.exoplayersample.base.BaseRepository
import com.omerakkoyun.exoplayersample.base.NetworkResult
import com.omerakkoyun.exoplayersample.data.remote.MovieResponse
import com.omerakkoyun.exoplayersample.data.service.MovieApiService
import com.omerakkoyun.exoplayersample.domain.repository.MovieRepository
import javax.inject.Inject

/**
 * Created by Omer AKKOYUN on 3.11.2024.
 */
class MovieRepositoryImpl @Inject constructor(private val movieApiService: MovieApiService) : BaseRepository(), MovieRepository {


    override suspend fun getByReleaseDateMovies(language: String, page: Int): NetworkResult<MovieResponse> {
        return safeApiRequest {
            movieApiService.getByReleaseDateMovies(language, page)
        }
    }

    override suspend fun getPopularMovies(language: String, page: Int): NetworkResult<MovieResponse> {
        return safeApiRequest {
            movieApiService.getPopularMovies(language, page)
        }
    }


    override suspend fun getTopRevenuesMovies(language: String, page: Int): NetworkResult<MovieResponse> {
        return safeApiRequest {
            movieApiService.getTopRevenuesMovies(language, page)
        }
    }


}