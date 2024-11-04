package com.omerakkoyun.exoplayersample.presentation.movie_list.adapter


import androidx.recyclerview.widget.RecyclerView
import com.omerakkoyun.exoplayersample.common.Constants.IMAGE_PATH
import com.omerakkoyun.exoplayersample.data.remote.ResultItem
import com.omerakkoyun.exoplayersample.databinding.ItemMovieViewBinding
import com.omerakkoyun.exoplayersample.utils.loadImageFromID

/**
 * Created by Omer AKKOYUN on 3.11.2024.
 */
class MovieViewHolder(private val binding: ItemMovieViewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ResultItem, itemClick: (ResultItem,position: Int) -> Unit){
        binding.imgMovie.setOnClickListener {
            itemClick(item,position)
        }
        binding.imgMovie.loadImageFromID(IMAGE_PATH + item.posterPath)
    }

}