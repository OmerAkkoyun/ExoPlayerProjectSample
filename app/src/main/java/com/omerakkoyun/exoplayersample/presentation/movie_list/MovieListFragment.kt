package com.omerakkoyun.exoplayersample.presentation.movie_list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.omerakkoyun.exoplayersample.base.BaseFragment
import com.omerakkoyun.exoplayersample.databinding.FragmentMovieListBinding


class MovieListFragment : BaseFragment<FragmentMovieListBinding, MovieListViewModel>() {
    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMovieListBinding {
        return FragmentMovieListBinding.inflate(inflater, container, false)
    }

    override fun initViewModel(): Class<MovieListViewModel> {
        return MovieListViewModel::class.java
    }

    override fun onCreateFinished() {

    }

    override fun attachListeners() {

    }

    override fun observeEvents() {

    }

}