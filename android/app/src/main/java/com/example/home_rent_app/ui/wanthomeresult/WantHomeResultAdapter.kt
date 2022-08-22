package com.example.home_rent_app.ui.wanthomeresult

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.home_rent_app.data.dto.WantedArticle
import com.example.home_rent_app.databinding.ItemWanthomeResultBinding

class WantHomeResultAdapter :
    ListAdapter<WantedArticle, WantHomeResultAdapter.WantHomeResultViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WantHomeResultViewHolder {
        val binding =
            ItemWanthomeResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WantHomeResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WantHomeResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class WantHomeResultViewHolder(private val binding: ItemWanthomeResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(wantedArticle: WantedArticle) {
            binding.wantedArticle = wantedArticle
        }
    }
}

object DiffCallBack : DiffUtil.ItemCallback<WantedArticle>() {
    override fun areItemsTheSame(oldItem: WantedArticle, newItem: WantedArticle): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WantedArticle, newItem: WantedArticle): Boolean {
        return oldItem == newItem
    }
}
