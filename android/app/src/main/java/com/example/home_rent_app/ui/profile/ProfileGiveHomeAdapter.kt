package com.example.home_rent_app.ui.profile

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.home_rent_app.data.dto.RentArticleBookmark
import com.example.home_rent_app.databinding.ItemGivehomeProfileBinding
import com.example.home_rent_app.ui.detail.DetailRentActivity
import com.example.home_rent_app.util.ItemIdSession
import javax.inject.Inject

class ProfileGiveHomeAdapter @Inject constructor(
    private val viewModel: ProfileViewModel,
    private val itemIdSession: ItemIdSession
) : ListAdapter<RentArticleBookmark, ProfileGiveHomeAdapter.ProfileGiveHomeViewHolder>(ProfileGiveAdapterDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileGiveHomeViewHolder {
        val binding =
            ItemGivehomeProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileGiveHomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileGiveHomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProfileGiveHomeViewHolder(private val binding: ItemGivehomeProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(rentArticleBookmark : RentArticleBookmark) {
            binding.rentArticleBookmark = rentArticleBookmark
            itemView.setOnClickListener {
                itemIdSession.itemId = rentArticleBookmark.id
                Intent(it.context, DetailRentActivity::class.java).run {
                    it.context.startActivity(this)
                }
            }
        }

        private fun delete(id: Int) {
            viewModel.deleteItem(id)
        }
    }
}

object ProfileGiveAdapterDiffCallBack : DiffUtil.ItemCallback<RentArticleBookmark>() {
    override fun areItemsTheSame(oldItem: RentArticleBookmark, newItem: RentArticleBookmark): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RentArticleBookmark, newItem: RentArticleBookmark): Boolean {
        return oldItem == newItem
    }
}
