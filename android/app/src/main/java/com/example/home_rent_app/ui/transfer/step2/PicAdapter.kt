package com.example.home_rent_app.ui.transfer.step2

import android.app.Activity
import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ContentResolver
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View.DRAG_FLAG_GLOBAL
import android.view.View.DRAG_FLAG_GLOBAL_URI_READ
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.core.util.component1
import androidx.core.util.component2
import androidx.draganddrop.DropHelper
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.ItemRoomPicBinding
import com.example.home_rent_app.util.PicControlListener
import com.example.home_rent_app.util.ShadowBuilder
import com.example.home_rent_app.util.getBitmap

class PicAdapter(
    private val contentResolver: ContentResolver,
    private val listener: PicControlListener
) : ListAdapter<Uri, PicAdapter.PicViewHolder>(PicDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicViewHolder {
        return PicViewHolder(
            ItemRoomPicBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    // 드래그 앤 드롭 성공시 데이터 변경하기
    override fun onBindViewHolder(holder: PicViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PicViewHolder(private val binding: ItemRoomPicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(uri: Uri) {
            binding.ivMainImage.setImageBitmap(getBitmap(contentResolver, uri))
            setRemoveClick()
            dragAndDrop(uri)
        }

        private fun setRemoveClick() {
            binding.ivBtnRemove.setOnClickListener {
                listener.removePic(adapterPosition)
            }
        }

        private fun dragAndDrop(uri: Uri) {
            binding.ivMainImage.setOnLongClickListener { view ->

                val dragData = ClipData.newUri(contentResolver, "image", uri)
                val item = ClipData.Item(adapterPosition.toString())
                val data = ClipData(adapterPosition.toString(), arrayOf(MIMETYPE_TEXT_PLAIN), item)
                val shadow = ShadowBuilder(view)
//                DRAG_FLAG_GLOBAL.or(DRAG_FLAG_GLOBAL_URI_READ)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    view.startDragAndDrop(
                        data,  // The data to be dragged
                        shadow,  // The drag shadow builder
                        null,      // No need to use local data
                        0    // Flags (not currently used, set to 0)
                    )
                } else {
                    Toast.makeText(binding.root.context, "버전이 낮아서 지원하지 않습니다.", Toast.LENGTH_SHORT)
                        .show()
                }

                true
            }

            DropHelper.configureView(
                binding.root.context as Activity,
                itemView,
                arrayOf(
                    MIMETYPE_TEXT_PLAIN,
                    "image/*",
                    "application/x-arc-uri-list" // Support external items on Chrome OS Android 9
                ),
                DropHelper.Options.Builder()
                    .setHighlightColor(getColor(binding.root.context, R.color.lightGray))
                    .build()
            ) { _, payload ->

                val item = payload.clip.getItemAt(0)
                val (_, remaining) = payload.partition { it == item }

                handleImageDrop(item)
                remaining
            }

        }

        private fun handleImageDrop(item: ClipData.Item) {
            listener.changePic(item.text.toString().toInt(), adapterPosition)
        }
    }

    private object PicDiffUtil : DiffUtil.ItemCallback<Uri>() {
        override fun areItemsTheSame(oldItem: Uri, newItem: Uri) =
            oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(oldItem: Uri, newItem: Uri) =
            oldItem == newItem

    }

}