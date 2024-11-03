package com.omerakkoyun.exoplayersample.presentation.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.omerakkoyun.exoplayersample.R
import com.omerakkoyun.exoplayersample.base.BaseFragment
import com.omerakkoyun.exoplayersample.common.Constants.IMAGE_PATH
import com.omerakkoyun.exoplayersample.common.Constants.MOVIE_ITEM
import com.omerakkoyun.exoplayersample.data.remote.ResultItem
import com.omerakkoyun.exoplayersample.databinding.FragmentMovieDetailsBinding
import com.omerakkoyun.exoplayersample.utils.loadImageFromID

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding, MovieDetailsViewModel>() {
    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMovieDetailsBinding {
        return FragmentMovieDetailsBinding.inflate(inflater,container,false)
    }

    override fun initViewModel(): Class<MovieDetailsViewModel> {
        return  MovieDetailsViewModel::class.java
    }

    override fun onCreateFinished() {
        val data = arguments?.getParcelable<ResultItem>(MOVIE_ITEM)
        if (data != null) {
            with(binding) {
                tvMovieTitle.text = data.title
                tvDescription.text = data.overview
                tvMovieScore.text = data.voteAverage.toString()
                tvMovieReleaseDate.text = data.releaseDate.toString()
                imgMovie.loadImageFromID(IMAGE_PATH + data.backdropPath)
            }
        }
    }

    override fun attachListeners() {
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.imgPlay.setOnClickListener {
            // go video player screen
            val bundle = Bundle()
            bundle.putParcelable(MOVIE_ITEM, arguments?.getParcelable(MOVIE_ITEM))
            findNavController().navigate(R.id.action_movieDetailsFragment_to_moviePlayerFragment,bundle)

        }
    }

    override fun observeEvents() {}

}