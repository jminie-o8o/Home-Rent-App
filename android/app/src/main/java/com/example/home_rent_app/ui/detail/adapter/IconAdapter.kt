package com.example.home_rent_app.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.ItemIconBinding

class IconAdapter : ListAdapter<String, IconAdapter.IconViewHolder>(IconDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        return IconViewHolder(
            ItemIconBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class IconViewHolder(private val binding: ItemIconBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(icon: String) {
            checkIcon(icon)
        }

        private fun checkIcon(icon: String) {
            val context = itemView.context
            when (icon) {
                context.getString(R.string.conditioner) -> {
                    setIconInfo(icon, R.drawable.ic_conditioner)
                }
                context.getString(R.string.washer) -> {
                    setIconInfo(icon, R.drawable.ic_washer)
                }
                context.getString(R.string.bed) -> {
                    setIconInfo(icon, R.drawable.ic_bed)
                }
                context.getString(R.string.refrigerator) -> {
                    setIconInfo(icon, R.drawable.ic_refrigerator)
                }
                context.getString(R.string.tv) -> {
                    setIconInfo(icon, R.drawable.ic_tv)
                }
                context.getString(R.string.closet) -> {
                    setIconInfo(icon, R.drawable.ic_closet)
                }
                context.getString(R.string.cctv) -> {
                    setIconInfo(icon, R.drawable.ic_cctv)
                }
                context.getString(R.string.video_phone) -> {
                    setIconInfo(icon, R.drawable.ic_video_phone)
                }
                context.getString(R.string.entrance) -> {
                    setIconInfo(icon, R.drawable.ic_entrance)
                }
            }
        }

        private fun setIconInfo(name: String, iconId: Int) {
            binding.ivIcon.setImageResource(iconId)
            binding.tvIconName.text = name
        }
    }

    private object IconDiffUtil : DiffUtil.ItemCallback<String>() {
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
