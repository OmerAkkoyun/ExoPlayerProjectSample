package com.omerakkoyun.exoplayersample.data.service

import com.omerakkoyun.exoplayersample.data.remote.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by Omer AKKOYUN on 3.11.2024.
 */
interface MovieApiService {

    // PopularMovies
    @Headers("accept: application/json")
    @GET("movie")
    suspend fun getPopularMovies(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("sort_by") sort_by: String = "popularity.desc",
    ): MovieResponse

    // Upcoming Movies
    @Headers("accept: application/json")
    @GET("movie")
    suspend fun getTopRevenuesMovies(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("sort_by") sort_by: String = "revenue.desc",
    ): MovieResponse

    // Now Playing Movies
    @Headers("accept: application/json")
    @GET("movie")
    suspend fun getByReleaseDateMovies(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("sort_by") sort_by: String = "vote_count.desc",
    ): MovieResponse
}
