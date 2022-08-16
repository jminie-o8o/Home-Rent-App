package com.example.home_rent_app.ui.transfer.step2

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.DragEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.home_rent_app.databinding.FragmentPicChoiceBinding
import com.example.home_rent_app.ui.transfer.TransferViewModel
import com.example.home_rent_app.util.ShadowBuilder
import com.example.home_rent_app.util.UiState
import com.example.home_rent_app.util.logger
import com.example.home_rent_app.util.repeatOnStarted
import kotlinx.coroutines.flow.collect

class PicChoiceFragment : Fragment() {

    private val binding: FragmentPicChoiceBinding by lazy {
        FragmentPicChoiceBinding.inflate(layoutInflater)
    }

    private val viewModel: TransferViewModel by activityViewModels()

    private val albumLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK && it.data != null) {
                val currentImageUri = it.data?.data
                try {
                    currentImageUri?.let {
                        if (Build.VERSION.SDK_INT < 28) {
                            val bitmap = MediaStore.Images.Media.getBitmap(
                                requireActivity().contentResolver,
                                currentImageUri
                            )
                            viewModel.setPicture(bitmap)
                        } else {
                            val source = ImageDecoder.createSource(
                                requireActivity().contentResolver,
                                currentImageUri
                            )
                            val bitmap = ImageDecoder.decodeBitmap(source)
                            viewModel.setPicture(bitmap)
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else if (it.resultCode == RESULT_CANCELED) {
                Toast.makeText(this.requireContext(), "사진 선택 취소", Toast.LENGTH_LONG).show()
            } else {
                logger("something wrong")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivRemove.setOnClickListener {
            binding.ivMainImage.visibility = View.GONE
            binding.ivRemove.visibility = View.GONE
            binding.ivMainImage.setImageResource(0)
            viewModel.removeMainPic()
        }

        binding.cvUploadPic.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            albumLauncher.launch(intent)
        }

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
                Toast.makeText(this.requireContext(), "버전이 낮아서 지원하지 않습니다.", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this.requireContext(), "Dragged data is $dragData", Toast.LENGTH_LONG).show()

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
                            Toast.makeText(this.requireContext(), "The drop was handled.", Toast.LENGTH_LONG)
                        else ->
                            Toast.makeText(this.requireContext(), "The drop didn't work.", Toast.LENGTH_LONG)
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

        val picAdapter = PicAdapter {
            viewModel.removePic(it)
        }

        binding.rvPicList.apply {
            adapter = picAdapter
            layoutManager = GridLayoutManager(this.context, 2, GridLayoutManager.VERTICAL, false)
        }

        repeatOnStarted {
            viewModel.picture.collect {
                picAdapter.submitList(it)
            }
        }

        repeatOnStarted {
            viewModel.mainPicture.collect {
                when (it) {
                    is UiState.Success -> {
                        binding.ivMainImage.visibility = View.VISIBLE
                        binding.ivRemove.visibility = View.VISIBLE
                        binding.ivMainImage.setImageBitmap(it.data)
                    }
                    is UiState.Error -> {
                        Toast.makeText(binding.root.context, it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        logger("Loading")
                    }
                }
            }
        }
    }
}
