package com.omerakkoyun.exoplayersample.presentation.movie_list

import android.os.Bundle
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
import com.omerakkoyun.exoplayersample.presentation.movie_list.adapter.MovieListPagingAdapter
import com.omerakkoyun.exoplayersample.utils.isInternetAvailable
import com.omerakkoyun.exoplayersample.utils.isTabletDevice
import com.omerakkoyun.exoplayersample.utils.loadImageFromID
import com.omerakkoyun.exoplayersample.utils.makeItVisible
import com.omerakkoyun.exoplayersample.utils.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : BaseFragment<FragmentMovieListBinding, MovieListViewModel>() {

    private lateinit var adapterSectionOne: MovieListPagingAdapter
    private lateinit var adapterSectionTwo: MovieListPagingAdapter
    private lateinit var adapterSectionThree: MovieListPagingAdapter


    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMovieListBinding {
        return FragmentMovieListBinding.inflate(inflater, container, false)
    }

    override fun initViewModel(): Class<MovieListViewModel> {
        return MovieListViewModel::class.java
    }

    override fun onCreateFinished() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshContent()
        }
    }

    private fun refreshContent() {
        if (requireContext().isInternetAvailable()) {  // İnternet kontrolü
            observeEvents()
            binding.swipeRefreshLayout.isRefreshing = false
        } else {
            binding.tvInternetError.text = resources.getString(R.string.connection_error)
            binding.tvInternetError.makeItVisible()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun attachListeners() {
        adapterSectionOne = MovieListPagingAdapter(::sectionClickListener)
        binding.rvSectionOne.adapter = adapterSectionOne

        adapterSectionTwo = MovieListPagingAdapter(::sectionClickListener)
        binding.rvSectionTwo.adapter = adapterSectionTwo

        adapterSectionThree = MovieListPagingAdapter(::sectionClickListener)
        binding.rvSectionThree.adapter = adapterSectionThree
    }

    override fun observeEvents() {
        with(viewModel) {

            getMoviesWithPaging(MovieRequestType.POPULAR).observe(viewLifecycleOwner, Observer {
                adapterSectionOne.submitData(viewLifecycleOwner.lifecycle, it)
            })

            getMoviesWithPaging(MovieRequestType.TOP_REVENUES).observe(viewLifecycleOwner, Observer {
                adapterSectionTwo.submitData(viewLifecycleOwner.lifecycle, it)
            })

            getMoviesWithPaging(MovieRequestType.BY_RELEASE_DATE).observe(viewLifecycleOwner, Observer {
                adapterSectionThree.submitData(viewLifecycleOwner.lifecycle, it)
            })

            errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
                    binding.tvInternetError.text = errorMessage
                binding.tvInternetError.setVisible( errorMessage != null)
            })

        }
    }


    // go details screen with movie item
    private fun sectionClickListener(data: ResultItem, position: Int) {
        if (requireActivity().isTabletDevice()) {
            setTabletViewData(data)
        } else {
            val bundle = Bundle()
            bundle.putParcelable(MOVIE_ITEM, data)
            findNavController().navigate(R.id.action_movieListFragment_to_movieDetailsFragment, bundle)
        }
    }

    private fun setTabletViewData(data: ResultItem) {
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
                bundle.putParcelable(MOVIE_ITEM, data)
                findNavController().navigate(R.id.action_movieListFragment_to_moviePlayerFragment, bundle)
            }
        }
    }
}