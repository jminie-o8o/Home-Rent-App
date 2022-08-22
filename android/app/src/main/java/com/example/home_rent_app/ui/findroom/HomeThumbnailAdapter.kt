package com.example.home_rent_app.ui.findroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.home_rent_app.databinding.ItemThumbnailBinding

class HomeThumbnailAdapter: ListAdapter<String, HomeThumbnailAdapter.HomeThumbnailViewHolder>(HomeThumbnailDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeThumbnailViewHolder {
        return HomeThumbnailViewHolder(
            ItemThumbnailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HomeThumbnailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HomeThumbnailViewHolder(private val binding: ItemThumbnailBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            binding.ivThumbnail.load(imageUrl)
        }
    }

    private object HomeThumbnailDiffUtil: DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem

    }

}