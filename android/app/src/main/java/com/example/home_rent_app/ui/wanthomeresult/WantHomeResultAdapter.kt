package com.example.home_rent_app.ui.wanthomeresult

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.home_rent_app.data.dto.WantedArticle
import com.example.home_rent_app.data.model.AddBookmarkRequest
import com.example.home_rent_app.databinding.ItemWanthomeResultBinding
import com.example.home_rent_app.ui.viewmodel.WantHomeResultViewModel
import com.example.home_rent_app.util.setLikeClickEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class WantHomeResultAdapter(private val viewModel: WantHomeResultViewModel) :
    ListAdapter<WantedArticle, WantHomeResultAdapter.WantHomeResultViewHolder>(DiffCallBack) {

    val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WantHomeResultViewHolder {
        val binding =
            ItemWanthomeResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WantHomeResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WantHomeResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class WantHomeResultViewHolder(private val binding: ItemWanthomeResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(wantedArticle: WantedArticle) {
            binding.wantedArticle = wantedArticle
            binding.btnLike.setLikeClickEvent(scope) {
                binding.btnLike.isSelected = binding.btnLike.isSelected != true
            }
            addBookmark(binding.btnLike.isSelected, wantedArticle)
        }

        private fun addBookmark(isSelected: Boolean, wantedArticle: WantedArticle) {
            if (isSelected) viewModel.addBookmark((AddBookmarkRequest(1, wantedArticle.id)))
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        scope.cancel()
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
