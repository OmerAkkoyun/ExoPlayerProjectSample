package com.omerakkoyun.exoplayersample.presentation.movie_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.omerakkoyun.exoplayersample.R
import com.omerakkoyun.exoplayersample.base.BaseFragment
import com.omerakkoyun.exoplayersample.common.Constants.IMAGE_PATH
import com.omerakkoyun.exoplayersample.common.Constants.MOVIE_ITEM
import com.omerakkoyun.exoplayersample.data.remote.ResultItem
import com.omerakkoyun.exoplayersample.databinding.FragmentMovieListBinding
import com.omerakkoyun.exoplayersample.enums.MovieRequestType
import com.omerakkoyun.exoplayersample.presentation.movie_list.adapter.MovieListRecyclerViewAdapter
import com.omerakkoyun.exoplayersample.utils.isTabletDevice
import com.omerakkoyun.exoplayersample.utils.loadImageFromID
import com.omerakkoyun.exoplayersample.utils.makeItVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : BaseFragment<FragmentMovieListBinding, MovieListViewModel>() {

    private lateinit var adapterSectionOne: MovieListRecyclerViewAdapter
    private lateinit var adapterSectionTwo: MovieListRecyclerViewAdapter
    private lateinit var adapterSectionThree: MovieListRecyclerViewAdapter

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMovieListBinding {
        return FragmentMovieListBinding.inflate(inflater, container, false)
    }

    override fun initViewModel(): Class<MovieListViewModel> {
        return MovieListViewModel::class.java
    }

    override fun onCreateFinished() {}

    override fun attachListeners() {
        adapterSectionOne = MovieListRecyclerViewAdapter(::sectionClickListener)
        binding.rvSectionOne.adapter = adapterSectionOne

        adapterSectionTwo = MovieListRecyclerViewAdapter(::sectionClickListener)
        binding.rvSectionTwo.adapter = adapterSectionTwo

        adapterSectionThree = MovieListRecyclerViewAdapter(::sectionClickListener)
        binding.rvSectionThree.adapter = adapterSectionThree
    }

    override fun observeEvents() {
        with(viewModel) {

            getMoviesWithPaging(MovieRequestType.POPULAR).observe(viewLifecycleOwner, Observer {
                adapterSectionOne.submitData(viewLifecycleOwner.lifecycle,it)
            })

            getMoviesWithPaging(MovieRequestType.TOP_REVENUES).observe(viewLifecycleOwner, Observer {
                adapterSectionTwo.submitData(viewLifecycleOwner.lifecycle,it)
            })

            getMoviesWithPaging(MovieRequestType.BY_RELEASE_DATE).observe(viewLifecycleOwner, Observer {
                adapterSectionThree.submitData(viewLifecycleOwner.lifecycle,it)
            })

        }
    }

    // go details screen with movie item
    private fun sectionClickListener(data: ResultItem){
        if (requireActivity().isTabletDevice()){
            setTabletViewData(data)
        }else{
            val bundle = Bundle()
            bundle.putParcelable(MOVIE_ITEM,data)
            findNavController().navigate(R.id.action_movieListFragment_to_movieDetailsFragment,bundle)
        }
    }

    private fun setTabletViewData( data: ResultItem){
        with(binding) {
            tvMovieTitle?.text = data.title
            tvDescription?.text = data.overview
            tvMovieScore?.text = data.voteAverage.toString()
            tvMovieReleaseDate?.text = data.releaseDate.toString()
            imgMovie?.loadImageFromID(IMAGE_PATH + data.backdropPath)

            imgPlay?.makeItVisible()
            imgStar?.makeItVisible()
            imgCalendar?.makeItVisible()

            imgPlay?.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable(MOVIE_ITEM,data)
                findNavController().navigate(R.id.action_movieListFragment_to_moviePlayerFragment,bundle)
            }
        }
    }
}