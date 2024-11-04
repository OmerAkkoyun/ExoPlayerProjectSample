package com.omerakkoyun.exoplayersample.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.omerakkoyun.exoplayersample.enums.MovieRequestType

/**
 * Created by Omer AKKOYUN on 4.11.2024.
 */
const val MOVIE_TABLE = "movie_table"

@Entity(tableName = MOVIE_TABLE)
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val posterPath: String,
    val voteAverage: Double,
    val movieType: MovieRequestType,
    val page: Int,
    val totalPages: Int,
    val totalResults: Int
) {
    constructor() : this(0, "", "", "", "", 0.0, MovieRequestType.POPULAR, 0, 0, 0)
}