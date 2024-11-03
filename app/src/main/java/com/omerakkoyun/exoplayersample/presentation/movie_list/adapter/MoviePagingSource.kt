package com.omerakkoyun.exoplayersample.presentation.movie_list.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.omerakkoyun.exoplayersample.common.Constants
import com.omerakkoyun.exoplayersample.data.remote.ResultItem
import com.omerakkoyun.exoplayersample.domain.usecase.MoviesByReleaseDateUseCase
import com.omerakkoyun.exoplayersample.domain.usecase.PopularMoviesUseCase
import com.omerakkoyun.exoplayersample.domain.usecase.TopRevenuesMoviesUseCase
import com.omerakkoyun.exoplayersample.enums.MovieRequestType
import javax.inject.Inject

/**
 * Created by Omer AKKOYUN on 3.11.2024.
 */


class MoviePagingSource @Inject constructor(
    private val popularMoviesUseCase: PopularMoviesUseCase,
    private val moviesByReleaseDateUseCase: MoviesByReleaseDateUseCase,
    private val topRevenuesMoviesUseCase: TopRevenuesMoviesUseCase,
    private val movieRequestType: MovieRequestType
) : PagingSource<Int, ResultItem>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultItem> {
        val page = params.key ?: 1
        val language = Constants.ENG

        return try {
            val response = when (movieRequestType) {
                MovieRequestType.BY_RELEASE_DATE -> moviesByReleaseDateUseCase.getByReleaseDateMovies(language, page).data
                MovieRequestType.TOP_REVENUES -> topRevenuesMoviesUseCase.getTopRevenuesMovies(language, page).data
                else -> popularMoviesUseCase.getPopularMovies(language, page).data
            }

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (response?.results!!.isEmpty()) null else page + 1
            return LoadResult.Page(data = response.results, prevKey = prevKey, nextKey = nextKey)

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultItem>): Int? {
        if (state.anchorPosition != null) {
            val anchorPage = state.closestPageToPosition(state.anchorPosition!!)
            if (anchorPage != null) {
                return if (anchorPage.prevKey != null) {
                    anchorPage.prevKey?.plus(1)
                } else {
                    anchorPage.nextKey?.minus(1)
                }
            }
        }
        return null
    }
}