package com.example.home_rent_app.ui.findroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.home_rent_app.data.model.Article
import com.example.home_rent_app.databinding.ItemSearchResultBinding

class HomeListAdapter: ListAdapter<Article, HomeListAdapter.TempViewHolder>(TempDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TempViewHolder {
        return TempViewHolder(ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TempViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TempViewHolder(private val binding:ItemSearchResultBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Article) {
            binding.item = item
            val viewPagerAdapter = HomeThumbnailAdapter()
            binding.vpThumbNailList.adapter = viewPagerAdapter
            val list = listOf("https://ifh.cc/g/sSr5Rr.png", "https://ifh.cc/g/sSr5Rr.png", "https://ifh.cc/g/sSr5Rr.png")
            viewPagerAdapter.submitList(list)
            submitList(currentList)
        }

    }

    private object TempDiffUtil: DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Article, newItem: Article) =
            oldItem == newItem

    }

}