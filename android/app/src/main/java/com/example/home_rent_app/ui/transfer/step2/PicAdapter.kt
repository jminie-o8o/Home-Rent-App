package com.example.home_rent_app.ui.transfer.step2

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.home_rent_app.databinding.ItemRoomPicBinding
import com.example.home_rent_app.util.ShadowBuilder

class PicAdapter(private val listener: (Int) -> Unit) : ListAdapter<Bitmap, PicAdapter.PicViewHolder>(PicDiffUtil) {

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

        fun bind(bitmap: Bitmap) {
            binding.ivMainImage.setImageBitmap(bitmap)
            setRemoveClick()
            dragAndDrop()
        }

        private fun setRemoveClick() {
            binding.ivBtnRemove.setOnClickListener {
                listener(adapterPosition)
            }
        }

        private fun dragAndDrop() {
            binding.ivMainImage.setOnLongClickListener { v ->
                val item = ClipData.Item(v.tag as? CharSequence)

                val dragData = ClipData(
                    v.tag as? CharSequence,
                    arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                    item)

                val myShadow = ShadowBuilder(v)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(dragData,  // The data to be dragged
                        myShadow,  // The drag shadow builder
                        null,      // No need to use local data
                        0          // Flags (not currently used, set to 0)
                    )
                } else {
                    Toast.makeText(binding.root.context, "버전이 낮아서 지원하지 않습니다.", Toast.LENGTH_SHORT).show()
                }

                // Indicate that the long-click was handled.
                true
            }

            binding.ivMainImage.setOnDragListener { v, e ->

                // Handles each of the expected events.
                when (e.action) {
                    DragEvent.ACTION_DRAG_STARTED -> {
                        // Determines if this View can accept the dragged data.
                        if (e.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                            // As an example of what your application might do, applies a blue color tint
                            // to the View to indicate that it can accept data.
                            (v as? ImageView)?.setColorFilter(Color.BLUE)

                            // Invalidate the view to force a redraw in the new tint.
                            v.invalidate()

                            // Returns true to indicate that the View can accept the dragged data.
                            true
                        } else {
                            // Returns false to indicate that, during the current drag and drop operation,
                            // this View will not receive events again until ACTION_DRAG_ENDED is sent.
                            false
                        }
                    }
                    DragEvent.ACTION_DRAG_ENTERED -> {
                        // Applies a green tint to the View.
                        (v as? ImageView)?.setColorFilter(Color.GREEN)

                        // Invalidates the view to force a redraw in the new tint.
                        v.invalidate()

                        // Returns true; the value is ignored.
                        true
                    }

                    DragEvent.ACTION_DRAG_LOCATION ->
                        // Ignore the event.
                        true
                    DragEvent.ACTION_DRAG_EXITED -> {
                        // Resets the color tint to blue.
                        (v as? ImageView)?.setColorFilter(Color.BLUE)

                        // Invalidates the view to force a redraw in the new tint.
                        v.invalidate()

                        // Returns true; the value is ignored.
                        true
                    }
                    DragEvent.ACTION_DROP -> {
                        // Gets the item containing the dragged data.
                        val item: ClipData.Item = e.clipData.getItemAt(0)

                        // Gets the text data from the item.
                        val dragData = item.text

                        // Displays a message containing the dragged data.
                        Toast.makeText(binding.root.context, "Dragged data is $dragData", Toast.LENGTH_LONG).show()

                        // Turns off any color tints.
                        (v as? ImageView)?.clearColorFilter()

                        // Invalidates the view to force a redraw.
                        v.invalidate()

                        // Returns true. DragEvent.getResult() will return true.
                        true
                    }

                    DragEvent.ACTION_DRAG_ENDED -> {
                        // Turns off any color tinting.
                        (v as? ImageView)?.clearColorFilter()

                        // Invalidates the view to force a redraw.
                        v.invalidate()

                        // Does a getResult(), and displays what happened.
                        when(e.result) {
                            true ->
                                Toast.makeText(binding.root.context, "The drop was handled.", Toast.LENGTH_LONG)
                            else ->
                                Toast.makeText(binding.root.context, "The drop didn't work.", Toast.LENGTH_LONG)
                        }.show()

                        // Returns true; the value is ignored.
                        true
                    }
                    else -> {
                        // An unknown action type was received.
                        Log.e("DragDrop Example", "Unknown action type received by View.OnDragListener.")
                        false
                    }
                }
            }
        }
    }

    private object PicDiffUtil : DiffUtil.ItemCallback<Bitmap>() {
        override fun areItemsTheSame(oldItem: Bitmap, newItem: Bitmap) =
            oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(oldItem: Bitmap, newItem: Bitmap) =
            oldItem.sameAs(newItem)

    }

}