package com.example.home_rent_app.ui.transfer.step2

import android.app.Activity
import android.content.ClipData
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ContentResolver
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.core.util.component1
import androidx.core.util.component2
import androidx.draganddrop.DropHelper
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.home_rent_app.R
import com.example.home_rent_app.data.model.RoomPicture
import com.example.home_rent_app.databinding.ItemRoomPicBinding
import com.example.home_rent_app.util.PicControlListener
import com.example.home_rent_app.util.ShadowBuilder
import com.example.home_rent_app.util.getBitmap

private const val MAIN = 0
private const val OTHER = 1

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
            drop(itemView, adapterPosition)
        }

        private fun setRemoveClick() {
            binding.ivBtnRemove.setOnClickListener {
                listener.removePic(adapterPosition)
            }
        }

        private fun setMainLabel(roomPicture: RoomPicture) {
            if(roomPicture.isMain) {
                binding.tvMainLabel.visibility = VISIBLE
            } else {
                binding.tvMainLabel.visibility = GONE
            }
        }

        private fun drag() {
            binding.ivMainImage.setOnLongClickListener { view ->
//                val dragDate = ClipData.newUri(contentResolver, "image", uri)
                val item = ClipData.Item(adapterPosition.toString())
                val data = ClipData(adapterPosition.toString(), arrayOf(MIMETYPE_TEXT_PLAIN), item)
                val shadow = ShadowBuilder(view)
//                DRAG_FLAG_GLOBAL.or(DRAG_FLAG_GLOBAL_URI_READ)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    view.startDragAndDrop(
                        data,  // The data to be dragged
                        shadow,  // The drag shadow builder
                        null,      // No need to use local data
                        DRAG_FLAG_GLOBAL    // Flags (not currently used, set to 0)
                    )
                } else {
                    Toast.makeText(binding.root.context, "버전이 낮아서 지원하지 않습니다.", Toast.LENGTH_SHORT)
                        .show()
                }

                true
            }

        }

//        private fun drop() {
//            DropHelper.configureView(
//                binding.root as Activity,
//                itemView,
//                arrayOf(
//                    MIMETYPE_TEXT_PLAIN,
//                    "image/*",
//                    "application/x-arc-uri-list" // Support external items on Chrome OS Android 9
//                ),
//                DropHelper.Options.Builder()
//                    .setHighlightColor(getColor(binding.root.context, R.color.lightGray))
//                    .build()
//            ) { _, payload ->
//
//                val item = payload.clip.getItemAt(0)
//                val (_, remaining) = payload.partition { it == item }
//
//                handleImageDrop(item)
//                remaining
//            }
//        }
//
//        private fun handleImageDrop(item: ClipData.Item) {
//            val beforePosition = item.text.toString().toInt()
//            listener.changePic(beforePosition, adapterPosition)
//
//            if(beforePosition == 0 || adapterPosition == 0) {
//                notifyItemChanged(beforePosition)
//                notifyItemChanged(adapterPosition)
//            }
//        }
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