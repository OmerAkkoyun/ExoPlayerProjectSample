package com.omerakkoyun.exoplayersample.presentation.movie_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.omerakkoyun.exoplayersample.data.remote.ResultItem
import com.omerakkoyun.exoplayersample.domain.usecase.MoviesByReleaseDateUseCase
import com.omerakkoyun.exoplayersample.domain.usecase.PopularMoviesUseCase
import com.omerakkoyun.exoplayersample.domain.usecase.TopRevenuesMoviesUseCase
import com.omerakkoyun.exoplayersample.enums.MovieRequestType
import com.omerakkoyun.exoplayersample.presentation.movie_list.adapter.MoviePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Omer AKKOYUN on 3.11.2024.
 */

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val popularMoviesUseCase: PopularMoviesUseCase,
    private val moviesByReleaseDateUseCase: MoviesByReleaseDateUseCase,
    private val topRevenuesMoviesUseCase: TopRevenuesMoviesUseCase
) : ViewModel() {

    fun getMoviesWithPaging(movieRequestType: MovieRequestType): LiveData<PagingData<ResultItem>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                MoviePagingSource(
                    popularMoviesUseCase = popularMoviesUseCase,
                    moviesByReleaseDateUseCase = moviesByReleaseDateUseCase,
                    topRevenuesMoviesUseCase = topRevenuesMoviesUseCase,
                    movieRequestType = movieRequestType
                )
            }
        ).liveData.cachedIn(viewModelScope)
    }

}