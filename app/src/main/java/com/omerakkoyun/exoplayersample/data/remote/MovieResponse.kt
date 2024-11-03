package com.omerakkoyun.exoplayersample.data.remote

import com.google.gson.annotations.SerializedName
/**
 * Created by Omer AKKOYUN on 3.11.2024.
 */
data class MovieResponse(
    @SerializedName("page")
    val page: Int?,

    @SerializedName("results")
    val results: List<ResultItem>?,

    @SerializedName("total_pages")
    val totalPages: Int?,

    @SerializedName("total_results")
    val totalResults: Int?
)