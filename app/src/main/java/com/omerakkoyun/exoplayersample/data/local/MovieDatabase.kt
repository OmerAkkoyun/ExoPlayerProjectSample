package com.omerakkoyun.exoplayersample.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Omer AKKOYUN on 4.11.2024.
 */
const val MOVIE_DATABASE_NAME = "movie.db"

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    MOVIE_DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}