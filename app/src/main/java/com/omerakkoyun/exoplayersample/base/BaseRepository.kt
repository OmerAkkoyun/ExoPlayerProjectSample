package com.omerakkoyun.exoplayersample.base

import android.util.Log
import com.google.gson.Gson
import com.omerakkoyun.exoplayersample.HiltApplication.Companion.getAppContext
import com.omerakkoyun.exoplayersample.R
import com.omerakkoyun.exoplayersample.data.remote.ErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/**
 * Created by Omer AKKOYUN on 3.11.2024.
 */
abstract class BaseRepository {

    suspend fun <T> safeApiRequest(apiRequest: suspend () -> T): NetworkResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                NetworkResult.Success(apiRequest.invoke())
            } catch (throwable: Throwable) {
                Log.e("BaseRepository", " Error safeApiRequest: $throwable")
                when (throwable) {

                    is HttpException -> {
                        // Http (404,503 ..)
                        NetworkResult.Error(false, errorBodyParser(throwable.response()?.errorBody()?.string()))
                    }

                    else -> NetworkResult.Error(true, getAppContext().resources.getString(R.string.connection_error))
                }
            }
        }
    }


    private fun errorBodyParser(errorBody: String?): String {
        return if (!errorBody.isNullOrEmpty()) {
            // JSON error body
            try {
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                errorResponse.message
            } catch (e: Exception) {
                getAppContext().resources.getString(R.string.unknown_error)
            }
        } else {
            getAppContext().resources.getString(R.string.unknown_error)
        }
    }
}