package com.omerakkoyun.exoplayersample.data.repository

import android.util.Log
import com.omerakkoyun.exoplayersample.base.BaseRepository
import com.omerakkoyun.exoplayersample.base.NetworkResult
import com.omerakkoyun.exoplayersample.data.local.MovieDao
import com.omerakkoyun.exoplayersample.data.local.MovieEntity
import com.omerakkoyun.exoplayersample.data.remote.MovieResponse
import com.omerakkoyun.exoplayersample.data.service.MovieApiService
import com.omerakkoyun.exoplayersample.domain.repository.MovieRepository
import com.omerakkoyun.exoplayersample.enums.MovieRequestType
import com.omerakkoyun.exoplayersample.utils.toMovieEntity
import com.omerakkoyun.exoplayersample.utils.toResultItem
import javax.inject.Inject

/**
 * Created by Omer AKKOYUN on 3.11.2024.
 */
class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService,
    private val movieDao: MovieDao
) : BaseRepository(), MovieRepository {

    override suspend fun getByReleaseDateMovies(language: String, page: Int): NetworkResult<MovieResponse> {
        return fetchMoviesWithCache(language, page, MovieRequestType.BY_RELEASE_DATE)
    }

    override suspend fun getPopularMovies(language: String, page: Int): NetworkResult<MovieResponse> {
        return fetchMoviesWithCache(language, page, MovieRequestType.POPULAR)
    }

    override suspend fun getTopRevenuesMovies(language: String, page: Int): NetworkResult<MovieResponse> {
        return fetchMoviesWithCache(language, page, MovieRequestType.TOP_REVENUES)
    }

    private suspend fun fetchMoviesWithCache(
        language: String,
        page: Int,
        movieType: MovieRequestType
    ): NetworkResult<MovieResponse> {
        Log.e("MovieRepositoryImpl","MovieRepositoryImpl -> İSTEK GELDİ")
        // Öncelikle API'den verileri al
        val response = safeApiRequest {
            when (movieType) {
                MovieRequestType.BY_RELEASE_DATE -> movieApiService.getByReleaseDateMovies(language, page)
                MovieRequestType.TOP_REVENUES -> movieApiService.getTopRevenuesMovies(language, page)
                MovieRequestType.POPULAR -> movieApiService.getPopularMovies(language, page)
            }
        }
        Log.e("MovieRepositoryImpl","MovieRepositoryImpl -> İSTEK GELDİ, RESPONSE -> $response")

        // API'den veri alındıysa, verileri cache'e kaydet
        if (response is NetworkResult.Success) {
            Log.e("MovieRepositoryImpl","MovieRepositoryImpl -> İSTEK GELDİ -> success")
            response.data?.results?.let { results ->
                val movieEntities = results.map {
                    it.movieRequestType = movieType
                    it.toMovieEntity(movieType, page, response.data.totalPages ?: 0, response.data.totalResults ?: 0)
                }
                if (movieEntities.isNotEmpty()) {
                    movieDao.insertMovies(movieEntities)
                }
            }
        }

        // Eğer API'den bir hata varsa, cache'den veri al
        if (response is NetworkResult.Error) {
            Log.e("MovieRepositoryImpl","MovieRepositoryImpl -> İSTEK GELDİ -> error -> cacheden aldı")
            val cachedMovies = movieDao.getMoviesByType(movieType)
            return if (cachedMovies.isNotEmpty()) {
                NetworkResult.Success(MovieResponse(
                    page = page,
                    results = cachedMovies.map { it.toResultItem() },
                    totalPages = cachedMovies.first().totalPages,
                    totalResults = cachedMovies.first().totalResults
                ))
            } else {
                Log.e("MovieRepositoryImpl","MovieRepositoryImpl -> İSTEK GELDİ, 78. satır -> RESPONSE -> $response")
                response // Hata durumunu döndür
            }
        }

        // API'den başarılı bir yanıt alınmadıysa, veritabanındaki verileri döndür
        Log.e("MovieRepositoryImpl","MovieRepositoryImpl -> İSTEK GELDİ, 84.satr RESPONSE -> $response")
        return response
    }
}