package com.example.home_rent_app.ui.wanthomeresult

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.home_rent_app.data.dto.WantedArticle
import com.example.home_rent_app.data.model.BookmarkRequest
import com.example.home_rent_app.databinding.ItemWanthomeResultBinding
import com.example.home_rent_app.ui.viewmodel.WantHomeResultViewModel
import com.example.home_rent_app.ui.wanthome.detail.WantHomeDetailActivity
import com.example.home_rent_app.util.ItemIdSession
import com.example.home_rent_app.util.UserSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class WantHomeResultAdapter @Inject constructor(
    private val viewModel: WantHomeResultViewModel,
    private val userSession: UserSession,
    private val itemIdSession: ItemIdSession
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
                itemIdSession.itemId = wantedArticle.id
                Intent(it.context, WantHomeDetailActivity::class.java).run {
                    it.context.startActivity(this)
                }
            }
        }

        private fun addBookmark(wantedArticle: WantedArticle) {
            viewModel.addBookmark((BookmarkRequest(userSession.userId ?: 0, wantedArticle.id)))
        }

        private fun deleteBookmark(wantedArticle: WantedArticle) {
            viewModel.deleteBookmark((BookmarkRequest(userSession.userId ?: 0, wantedArticle.id)))
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
