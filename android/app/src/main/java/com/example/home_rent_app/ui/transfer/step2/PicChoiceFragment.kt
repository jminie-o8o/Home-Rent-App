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
import com.example.home_rent_app.util.*
import kotlinx.coroutines.flow.collect

class PicChoiceFragment : Fragment(), PicControlListener {

    private val binding: FragmentPicChoiceBinding by lazy {
        FragmentPicChoiceBinding.inflate(layoutInflater)
    }

    private val viewModel: TransferViewModel by activityViewModels()

    private val albumLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK && it.data != null) {
//                val currentImageUri = it.data?.data
                try {
                    val clipData = it?.data?.clipData
                    val clipDataSize = clipData?.itemCount
                    if (clipData == null) { //이미지를 하나만 선택할 경우 clipData가 null이 올수 있음
                        val selectedImageUri = it?.data?.data!!
                        viewModel.setPictureUri(selectedImageUri)
                    } else {
                        clipData.let { data ->
                            for (i in 0 until clipDataSize!!) { //선택 한 사진수만큼 반복
                                val selectedImageUri = data.getItemAt(i).uri
                                viewModel.setPictureUri(selectedImageUri)
                            }
                        }
                    }
//                    currentImageUri?.let { uri ->
//                        if (Build.VERSION.SDK_INT < 28) {
//                            val bitmap = MediaStore.Images.Media.getBitmap(
//                                requireActivity().contentResolver,
//                                currentImageUri
//                            )
//                            viewModel.setPictureUri(uri)
//                        } else {
//                            val source = ImageDecoder.createSource(
//                                requireActivity().contentResolver,
//                                currentImageUri
//                            )
//                            val bitmap = ImageDecoder.decodeBitmap(source)
//                            viewModel.setPictureUri(uri)
//                        }
//                    }
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
            viewModel.removeMainPicUri()
        }

        binding.cvUploadPic.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            albumLauncher.launch(Intent.createChooser(intent, "Select Picture"))
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

        val picAdapter = PicAdapter(requireActivity().contentResolver, this)

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
                        val bitmap = getBitmap(requireActivity().contentResolver, it.data)
                        binding.ivMainImage.setImageBitmap(bitmap)
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

    override fun removePic(position: Int) {
        viewModel.removePicUri(position)
    }

    override fun changePic(beforePosition: Int, targetPosition: Int) {
        viewModel.replacePic(beforePosition, targetPosition)
    }

}
