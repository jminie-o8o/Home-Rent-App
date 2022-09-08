package com.example.home_rent_app.ui.bookmark.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.home_rent_app.data.dto.WantedArticleBookmark
import com.example.home_rent_app.databinding.ItemWanthomeBookmarkBinding

class BookmarkWantHomeAdapter(
    private val goToDetail: (Int) -> Unit,
    private val deleteBookmark: (Int) -> Unit
) : ListAdapter<WantedArticleBookmark, BookmarkWantHomeAdapter.BookmarkWantHomeViewHolder>(
    BookmarkAdapterDiffCallBack
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkWantHomeViewHolder {
        val binding =
            ItemWanthomeBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkWantHomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkWantHomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BookmarkWantHomeViewHolder(private val binding: ItemWanthomeBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(wantedArticleBookmark: WantedArticleBookmark) {
            binding.bookmarkWantedBookmark = wantedArticleBookmark
            binding.btnLike.isChecked = true
            binding.btnLike.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) deleteBookmark(wantedArticleBookmark.id)
            }
            itemView.setOnClickListener {
                goToDetail(wantedArticleBookmark.id)
            }
        }
    }
}

object BookmarkAdapterDiffCallBack : DiffUtil.ItemCallback<WantedArticleBookmark>() {
    override fun areItemsTheSame(oldItem: WantedArticleBookmark, newItem: WantedArticleBookmark): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WantedArticleBookmark, newItem: WantedArticleBookmark): Boolean {
        return oldItem == newItem
    }
}
