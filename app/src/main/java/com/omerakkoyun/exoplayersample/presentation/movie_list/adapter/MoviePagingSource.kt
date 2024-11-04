package com.omerakkoyun.exoplayersample.presentation.movie_list.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.omerakkoyun.exoplayersample.HiltApplication.Companion.getAppContext
import com.omerakkoyun.exoplayersample.R
import com.omerakkoyun.exoplayersample.common.Constants
import com.omerakkoyun.exoplayersample.data.local.MovieDao
import com.omerakkoyun.exoplayersample.data.local.MovieEntity
import com.omerakkoyun.exoplayersample.data.remote.ResultItem
import com.omerakkoyun.exoplayersample.domain.repository.MovieRepository
import com.omerakkoyun.exoplayersample.domain.usecase.MoviesByReleaseDateUseCase
import com.omerakkoyun.exoplayersample.domain.usecase.PopularMoviesUseCase
import com.omerakkoyun.exoplayersample.domain.usecase.TopRevenuesMoviesUseCase
import com.omerakkoyun.exoplayersample.enums.MovieRequestType
import com.omerakkoyun.exoplayersample.utils.isInternetAvailable
import javax.inject.Inject

/**
 * Created by Omer AKKOYUN on 3.11.2024.
 */


class MoviePagingSource @Inject constructor(
    private val popularMoviesUseCase: PopularMoviesUseCase,
    private val moviesByReleaseDateUseCase: MoviesByReleaseDateUseCase,
    private val topRevenuesMoviesUseCase: TopRevenuesMoviesUseCase,
    private val movieRequestType: MovieRequestType,
    private val onError: (String?) -> Unit
) : PagingSource<Int, ResultItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultItem> {
        val page = params.key ?: 1
        val language = Constants.ENG

        if (!getAppContext().isInternetAvailable()) {
            // İnternet yoksa
            onError(getAppContext().resources.getString(R.string.connection_error))
        }else{
            onError(null)
        }

        return try {
            // UseCase üzerinden verileri al
            val response = when (movieRequestType) {
                MovieRequestType.BY_RELEASE_DATE -> moviesByReleaseDateUseCase.getByReleaseDateMovies(language, page)
                MovieRequestType.TOP_REVENUES -> topRevenuesMoviesUseCase.getTopRevenuesMovies(language, page)
                else -> popularMoviesUseCase.getPopularMovies(language, page)
            }

            val results = response.data?.results ?: emptyList()
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (results.isEmpty()) null else page + 1

            LoadResult.Page(data = results, prevKey = prevKey, nextKey = nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}