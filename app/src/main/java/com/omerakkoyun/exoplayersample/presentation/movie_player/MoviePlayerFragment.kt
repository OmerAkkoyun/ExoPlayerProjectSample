package com.omerakkoyun.exoplayersample.presentation.movie_player

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import com.omerakkoyun.exoplayersample.base.BaseFragment
import com.omerakkoyun.exoplayersample.common.Constants.DRM_TEST_LICENSE_URL
import com.omerakkoyun.exoplayersample.common.Constants.MEDIA_TEST_URI
import com.omerakkoyun.exoplayersample.common.Constants.MOVIE_ITEM
import com.omerakkoyun.exoplayersample.common.Constants.PLAYBACK_POSITION
import com.omerakkoyun.exoplayersample.data.remote.ResultItem
import com.omerakkoyun.exoplayersample.databinding.FragmentMoviePlayerBinding
import com.omerakkoyun.exoplayersample.utils.hideSystemBars
import com.omerakkoyun.exoplayersample.utils.isLandscape
import com.omerakkoyun.exoplayersample.utils.makeItGone
import com.omerakkoyun.exoplayersample.utils.makeItVisible


class MoviePlayerFragment : BaseFragment<FragmentMoviePlayerBinding, MoviePlayerViewModel>() {
    private var exoPlayer: ExoPlayer? = null
    private var playbackPosition: Long = 0

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMoviePlayerBinding {
        return FragmentMoviePlayerBinding.inflate(inflater, container, false)
    }

    override fun initViewModel(): Class<MoviePlayerViewModel> {
        return MoviePlayerViewModel::class.java
    }
    override fun observeEvents() {}
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION)
            checkFullScreen()
        }

        initializePlayer()

        // set default mode when user click back
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                findNavController().popBackStack()
            }
        })
    }

    override fun onCreateFinished() {
        val data = arguments?.getParcelable<ResultItem>(MOVIE_ITEM)
        if (data != null) {
            with(binding) {
                tvMovieTitle.text = data.title
                tvDescription.text = data.overview
            }
        }

    }

    override fun attachListeners() {
        binding.btnFullscreen.setOnClickListener {
            if (activity?.isLandscape() == true) {
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } else {
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(PLAYBACK_POSITION, exoPlayer!!.currentPosition)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exoPlayer?.release()
        exoPlayer = null
    }

    override fun onPause() {
        super.onPause()
        exoPlayer?.playWhenReady = false
    }

    override fun onResume() {
        super.onResume()
        exoPlayer?.playWhenReady = true
    }


    private fun checkFullScreen(){
        if (activity?.isLandscape() == true) {
            binding.lnLayoutInfo.makeItGone()
            activity?.hideSystemBars()
        } else {
            binding.lnLayoutInfo.makeItVisible()
        }
    }

    private fun initializePlayer() {
        exoPlayer = ExoPlayer.Builder(requireContext()).build()

        // License
        val drmConfiguration = MediaItem.DrmConfiguration.Builder(C.WIDEVINE_UUID)
            .setLicenseUri(DRM_TEST_LICENSE_URL)
            .build()

        // Media
        val mediaItem = MediaItem.Builder()
            .setUri(MEDIA_TEST_URI)
            .setDrmConfiguration(drmConfiguration)
            .build()

        // Settings
        exoPlayer?.apply {
            setMediaItem(mediaItem)
            prepare()
            exoPlayer?.seekTo(playbackPosition)
            playWhenReady = true
            checkFullScreen()
        }

        binding.playerView.player = exoPlayer

    }
}