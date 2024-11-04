package com.omerakkoyun.exoplayersample.domain.usecase

import com.omerakkoyun.exoplayersample.base.NetworkResult
import com.omerakkoyun.exoplayersample.data.remote.MovieResponse
import com.omerakkoyun.exoplayersample.domain.repository.MovieRepository
import javax.inject.Inject

/**
 * Created by Omer AKKOYUN on 3.11.2024.
 */
class MoviesByReleaseDateUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend fun getByReleaseDateMovies(language: String, page: Int): NetworkResult<MovieResponse> {
        return movieRepository.getByReleaseDateMovies(language, page)
    }
}