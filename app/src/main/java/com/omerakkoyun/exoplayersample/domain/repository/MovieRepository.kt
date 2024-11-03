package com.omerakkoyun.exoplayersample.domain.repository

import com.omerakkoyun.exoplayersample.base.NetworkResult
import com.omerakkoyun.exoplayersample.data.remote.MovieResponse

/**
 * Created by Omer AKKOYUN on 3.11.2024.
 */
interface MovieRepository {
    suspend fun getPopularMovies(language: String, page: Int): NetworkResult<MovieResponse>
    suspend fun getTopRevenuesMovies(language: String, page: Int): NetworkResult<MovieResponse>
    suspend fun getByReleaseDateMovies(language: String, page: Int): NetworkResult<MovieResponse>
}