package com.omerakkoyun.exoplayersample.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.omerakkoyun.exoplayersample.enums.MovieRequestType

/**
 * Created by Omer AKKOYUN on 4.11.2024.
 */


@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movieEntities: List<MovieEntity>)

    @Query("SELECT * FROM movie_table WHERE movieType = :movieType")
    suspend fun getMoviesByType(movieType: MovieRequestType): List<MovieEntity>

    @Query("DELETE FROM movie_table")
    suspend fun clearMovies()
}