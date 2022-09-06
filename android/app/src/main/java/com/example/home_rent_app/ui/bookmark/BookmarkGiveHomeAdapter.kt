package com.example.home_rent_app.ui.bookmark

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.home_rent_app.data.dto.RentArticleBookmark
import com.example.home_rent_app.data.model.BookmarkRequest
import com.example.home_rent_app.databinding.ItemGivehomeBookmarkBinding
import com.example.home_rent_app.ui.detail.DetailRentActivity
import com.example.home_rent_app.util.ItemIdSession
import com.example.home_rent_app.util.UserSession
import javax.inject.Inject

class BookmarkGiveHomeAdapter @Inject constructor(
    private val viewModel: BookmarkViewModel,
    private val userSession: UserSession,
    private val itemIdSession: ItemIdSession
) : ListAdapter<RentArticleBookmark, BookmarkGiveHomeAdapter.BookmarkGiveHomeViewHolder>(
    BookmarkGiveAdapterDiffCallBack
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookmarkGiveHomeViewHolder {
        val binding =
            ItemGivehomeBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkGiveHomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkGiveHomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BookmarkGiveHomeViewHolder(private val binding: ItemGivehomeBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(rentArticleBookmark: RentArticleBookmark) {
            binding.rentArticleBookmark = rentArticleBookmark
            binding.cbRecommend.isChecked = true
            binding.cbRecommend.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) deleteBookmark(rentArticleBookmark)
            }
            itemView.setOnClickListener {
                itemIdSession.itemId = rentArticleBookmark.id
                Intent(it.context, DetailRentActivity::class.java).run {
                    it.context.startActivity(this)
                }
            }
        }

        private fun deleteBookmark(rentArticleBookmark: RentArticleBookmark) {
            viewModel.deleteGiveHomeBookmark(
                BookmarkRequest(
                    userSession.userId ?: 0,
                    rentArticleBookmark.id
                )
            )
        }
    }
}

object BookmarkGiveAdapterDiffCallBack : DiffUtil.ItemCallback<RentArticleBookmark>() {
    override fun areItemsTheSame(
        oldItem: RentArticleBookmark,
        newItem: RentArticleBookmark
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: RentArticleBookmark,
        newItem: RentArticleBookmark
    ): Boolean {
        return oldItem == newItem
    }
}
