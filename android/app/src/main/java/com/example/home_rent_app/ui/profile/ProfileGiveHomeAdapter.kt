package com.example.home_rent_app.ui.profile

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.home_rent_app.R
import com.example.home_rent_app.data.dto.RentArticleProfile
import com.example.home_rent_app.databinding.ItemGivehomeProfileBinding
import com.example.home_rent_app.ui.detail.DetailRentActivity
import com.example.home_rent_app.util.ItemIdSession
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ProfileGiveHomeAdapter @Inject constructor(
    private val viewModel: ProfileViewModel,
    private val itemIdSession: ItemIdSession,
    @ApplicationContext private val context: Context
) : ListAdapter<RentArticleProfile, ProfileGiveHomeAdapter.ProfileGiveHomeViewHolder>(ProfileGiveAdapterDiffCallBack) {

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
        fun bind(rentArticleProfile: RentArticleProfile) {
            binding.rentArticleProfile = rentArticleProfile
            itemView.setOnClickListener {
                itemIdSession.itemId = rentArticleProfile.id
                Intent(it.context, DetailRentActivity::class.java).run {
                    it.context.startActivity(this)
                }
            }
            binding.btnMoreAction.setOnClickListener {
                showPopup(it, rentArticleProfile.id)
            }
        }

        private fun showPopup(view: View, id: Int) {
            val popup = PopupMenu(context, view)
            popup.menuInflater.inflate(R.menu.prifile_menu_item, popup.menu)
            popup.show()
            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_delete -> {
                        delete(id)
                        return@setOnMenuItemClickListener true
                    }
                    else -> {
                        return@setOnMenuItemClickListener true
                    }
                }
            }
        }

        private fun delete(id: Int) {
            viewModel.deleteGiveItem(id)
        }
    }
}

object ProfileGiveAdapterDiffCallBack : DiffUtil.ItemCallback<RentArticleProfile>() {
    override fun areItemsTheSame(oldItem: RentArticleProfile, newItem: RentArticleProfile): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RentArticleProfile, newItem: RentArticleProfile): Boolean {
        return oldItem == newItem
    }
}
