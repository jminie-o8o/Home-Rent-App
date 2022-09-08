package com.example.home_rent_app.ui.renthome.step2

import android.content.ClipData
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ContentResolver
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.View.DRAG_FLAG_GLOBAL
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.home_rent_app.data.model.RoomPicture
import com.example.home_rent_app.databinding.ItemRoomPicBinding
import com.example.home_rent_app.util.PicControlListener
import com.example.home_rent_app.util.ShadowBuilder
import com.example.home_rent_app.util.getBitmap
import com.example.home_rent_app.util.logger

class PicAdapter(
    private val contentResolver: ContentResolver,
    private val listener: PicControlListener,
    private val drop: (View, Int) -> Unit
) : ListAdapter<RoomPicture, PicAdapter.PicViewHolder>(PicDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicViewHolder {
        return PicViewHolder(
            ItemRoomPicBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PicViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PicViewHolder(private val binding: ItemRoomPicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(roomPicture: RoomPicture) {
            binding.ivMainImage.setImageBitmap(getBitmap(contentResolver, roomPicture.uri))
            setMainLabel(roomPicture)
            setRemoveClick()
            drag()
            drop(itemView, bindingAdapterPosition)
            logger("bind : $bindingAdapterPosition ${itemView.id}")
            // bind 후에 포지션이 변하지 않음
        }

        private fun setRemoveClick() {
            binding.ivBtnRemove.setOnClickListener {
                listener.removePic(bindingAdapterPosition)
            }
        }

        private fun setMainLabel(roomPicture: RoomPicture) {
            if (roomPicture.isMain) {
                binding.tvMainLabel.visibility = VISIBLE
            } else {
                binding.tvMainLabel.visibility = GONE
            }
        }

        private fun drag() {
            binding.ivMainImage.setOnLongClickListener { view ->
                val item = ClipData.Item(bindingAdapterPosition.toString())
                val data = ClipData(bindingAdapterPosition.toString(), arrayOf(MIMETYPE_TEXT_PLAIN), item)
                val shadow = ShadowBuilder(view)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    view.startDragAndDrop(
                        data, // The data to be dragged
                        shadow, // The drag shadow builder
                        null, // No need to use local data
                        DRAG_FLAG_GLOBAL // Flags (not currently used, set to 0)
                    )
                } else {
                    Toast.makeText(binding.root.context, "버전이 낮아서 지원하지 않습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
                true
            }
        }
    }

    private object PicDiffUtil : DiffUtil.ItemCallback<RoomPicture>() {
        override fun areItemsTheSame(oldItem: RoomPicture, newItem: RoomPicture): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RoomPicture, newItem: RoomPicture): Boolean {
            return oldItem.isMain == newItem.isMain
        }
    }
}
