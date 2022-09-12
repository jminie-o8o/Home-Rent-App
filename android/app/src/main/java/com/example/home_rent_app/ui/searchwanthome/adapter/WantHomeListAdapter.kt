package com.example.home_rent_app.ui.searchwanthome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.home_rent_app.data.dto.WantedArticle
import com.example.home_rent_app.databinding.ItemWanthomeResultBinding
import javax.inject.Inject

class WantHomeResultAdapter @Inject constructor(
    private val goToDetail: (Int) -> Unit,
    private val addBookmark: (Int) -> Unit,
    private val deleteBookmark: (Int) -> Unit
) : PagingDataAdapter<WantedArticle, WantHomeResultAdapter.WantHomeResultViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WantHomeResultViewHolder {
        val binding =
            ItemWanthomeResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WantHomeResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WantHomeResultViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class WantHomeResultViewHolder(private val binding: ItemWanthomeResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(wantedArticle: WantedArticle) {
            binding.wantedArticle = wantedArticle
            checkBookmark(wantedArticle)
            binding.btnLike.setOnCheckedChangeListener { _, isChecked ->
                when (isChecked) {
                    true -> {
                        addBookmark(wantedArticle)
                    }
                    false -> {
                        deleteBookmark(wantedArticle)
                    }
                }
            }
            itemView.setOnClickListener {
                goToDetail(wantedArticle.id)
            }
        }

        private fun addBookmark(wantedArticle: WantedArticle) {
            addBookmark(wantedArticle.id)
        }

        private fun deleteBookmark(wantedArticle: WantedArticle) {
            deleteBookmark(wantedArticle.id)
        }

        private fun checkBookmark(wantedArticle: WantedArticle) {
            if (wantedArticle.bookmarked) binding.btnLike.isChecked = true
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
