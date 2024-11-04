package com.omerakkoyun.exoplayersample.domain.usecase

import com.omerakkoyun.exoplayersample.base.NetworkResult
import com.omerakkoyun.exoplayersample.data.remote.MovieResponse
import com.omerakkoyun.exoplayersample.domain.repository.MovieRepository
import javax.inject.Inject


/**
 * Created by Omer AKKOYUN on 3.11.2024.
 */
class TopRevenuesMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend fun getTopRevenuesMovies(language: String, page: Int): NetworkResult<MovieResponse> {
        return movieRepository.getTopRevenuesMovies(language, page)
    }
}