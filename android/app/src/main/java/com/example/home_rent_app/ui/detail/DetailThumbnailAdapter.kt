package com.example.home_rent_app.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.ItemThumbnailBinding
import com.example.home_rent_app.util.logger

class DetailThumbnailAdapter(
    private val setPicCount: (Int, Int) -> Unit
): ListAdapter<String, DetailThumbnailAdapter.DetailThumbnailViewHolder>(HomeThumbnailDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailThumbnailViewHolder {
        return DetailThumbnailViewHolder(
            ItemThumbnailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DetailThumbnailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DetailThumbnailViewHolder(private val binding: ItemThumbnailBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            logger("imageUrl : $imageUrl")
            binding.ivThumbnail.load(imageUrl) {
                crossfade(true)
                error(R.drawable.ic_close)
            }
            setPicCount(absoluteAdapterPosition, currentList.size)
        }
    }

    private object HomeThumbnailDiffUtil: DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem

    }

}