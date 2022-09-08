package com.example.home_rent_app.ui.profile.adapter

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
import com.example.home_rent_app.data.dto.WantArticleProfile
import com.example.home_rent_app.databinding.ItemWanthomeProfileBinding
import com.example.home_rent_app.ui.profile.viewmodel.ProfileViewModel
import com.example.home_rent_app.ui.wanthome.detail.WantHomeDetailActivity
import com.example.home_rent_app.util.ItemIdSession
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ProfileWantHomeAdapter @Inject constructor(
    private val viewModel: ProfileViewModel,
    private val itemIdSession: ItemIdSession,
    @ApplicationContext private val context: Context
) : ListAdapter<WantArticleProfile, ProfileWantHomeAdapter.ProfileWantHomeViewHolder>(
    ProfileWantAdapterDiffCallBack
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileWantHomeViewHolder {
        val binding =
            ItemWanthomeProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileWantHomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileWantHomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProfileWantHomeViewHolder(private val binding: ItemWanthomeProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(wantArticleProfile: WantArticleProfile) {
            binding.wantArticleProfile = wantArticleProfile
            itemView.setOnClickListener {
                itemIdSession.itemId = wantArticleProfile.id
                Intent(it.context, WantHomeDetailActivity::class.java).run {
                    it.context.startActivity(this)
                }
            }
            binding.btnMoreAction.setOnClickListener {
                showPopup(it, wantArticleProfile.id)
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
            viewModel.deleteWantItem(id)
        }
    }
}

object ProfileWantAdapterDiffCallBack : DiffUtil.ItemCallback<WantArticleProfile>() {
    override fun areItemsTheSame(oldItem: WantArticleProfile, newItem: WantArticleProfile): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WantArticleProfile, newItem: WantArticleProfile): Boolean {
        return oldItem == newItem
    }
}
