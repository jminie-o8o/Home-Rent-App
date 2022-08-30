package com.example.home_rent_app.ui.findroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.home_rent_app.data.model.Article
import com.example.home_rent_app.databinding.ItemHomeListBinding

class HomeListAdapter(private val listener: (Int) -> Unit): ListAdapter<Article, HomeListAdapter.TempViewHolder>(TempDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TempViewHolder {
        return TempViewHolder(ItemHomeListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TempViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TempViewHolder(private val binding:ItemHomeListBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Article) {
            binding.item = item
            val viewPagerAdapter = HomeThumbnailAdapter()
            binding.vpThumbNailList.adapter = viewPagerAdapter
//            viewPagerAdapter.submitList(item.houseImage)
            setOnHomeClick(item)
        }

        private fun setOnHomeClick(item: Article) {
            itemView.setOnClickListener {
                listener(item.id)
            }
        }

    }

    private object TempDiffUtil: DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Article, newItem: Article) =
            oldItem == newItem

    }

}