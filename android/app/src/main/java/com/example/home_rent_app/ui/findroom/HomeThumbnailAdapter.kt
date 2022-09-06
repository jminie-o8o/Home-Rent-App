package com.example.home_rent_app.ui.findroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.ItemThumbnailBinding
import com.example.home_rent_app.util.logger

class HomeThumbnailAdapter : ListAdapter<String, HomeThumbnailAdapter.HomeThumbnailViewHolder>(HomeThumbnailDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeThumbnailViewHolder {
        return HomeThumbnailViewHolder(
            ItemThumbnailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HomeThumbnailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HomeThumbnailViewHolder(private val binding: ItemThumbnailBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            logger("imageUrl : $imageUrl")
            val circularProgressDrawable = CircularProgressDrawable(binding.root.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            binding.ivThumbnail.load(imageUrl) {
                crossfade(true)
                transformations(RoundedCornersTransformation(30f, 30f, 30f, 30f))
                placeholder(circularProgressDrawable)
                error(R.drawable.ic_close)
            }
        }
    }

    private object HomeThumbnailDiffUtil : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem
    }
}
