package com.example.home_rent_app.ui.renthome.step2

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.*
import androidx.core.util.component1
import androidx.core.util.component2
import androidx.draganddrop.DropHelper
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.home_rent_app.BuildConfig
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.FragmentPicChoiceBinding
import com.example.home_rent_app.ui.viewmodel.RentHomeViewModel
import com.example.home_rent_app.util.*
import com.example.home_rent_app.util.Constants.REQUIRED_PERMISSIONS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PicChoiceFragment : Fragment(), PicControlListener {

    private val binding: FragmentPicChoiceBinding by lazy {
        FragmentPicChoiceBinding.inflate(layoutInflater)
    }

    private val viewModel: RentHomeViewModel by activityViewModels()

    private lateinit var picAdapter: PicAdapter

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { isGranted ->
            val list = isGranted.filter { !it.value }.map { it.key }.toList()

            if(list.isNotEmpty()) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), list[0])) {
                    Toast.makeText(requireContext(), "권한 설정을 하지 않으면 어플을 사용할 수 없습니다.", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        .setData(Uri.parse("package:" + BuildConfig.APPLICATION_ID))
                    startActivity(intent)
                }
            }
        }


    private val albumLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK && it.data != null) {
                try {
                    val clipData = it?.data?.clipData
                    val clipDataSize = clipData?.itemCount
                    if (clipDataSize != null && clipDataSize > 6) {
                        Toast.makeText(context, "사진은 6개까지 선택이 가능합니다.", Toast.LENGTH_SHORT).show()
                        return@registerForActivityResult
                    }
                    if (clipData == null) { // 이미지를 하나만 선택할 경우 clipData가 null이 올수 있음
                        val selectedImageUri = it?.data?.data ?: Uri.EMPTY
                        viewModel.setPictureUri(selectedImageUri)
                    } else {
                        clipData.let { data ->
                            for (i in 0 until clipDataSize!!) { // 선택 한 사진수만큼 반복
                                val selectedImageUri = data.getItemAt(i).uri ?: Uri.EMPTY
                                viewModel.setPictureUri(selectedImageUri)
                            }
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

        setUploadButton()
        setNextButton()
        setAdapter()
        setPictureObserver()
        setCheckPictureMax()
        setPictureUrlObserver()
        setBackButton()
    }

    private fun setAdapter() {
        picAdapter = PicAdapter(requireActivity().contentResolver, this) { itemView, position ->
            logger("drop top : $position ${itemView.hashCode()}")
            drop(itemView, position)
        }

        binding.rvPicList.apply {
            adapter = picAdapter
            layoutManager = GridLayoutManager(this.context, 2, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun setPictureUrlObserver() {
        repeatOnStarted {
            viewModel.houseImages.collect {
                when (it) {
                    is UiState.Error -> {
                        logger("NetWork Error ${it.message}")
                    }
                    is UiState.Success -> {
                        // url 확인용 추후 삭제 예정
                        val images = it.data.images
                        images.forEach { logger("imageUrl : $it") }
                    }
                    else -> {
                        logger("Loading")
                    }
                }
            }
        }
    }

    private fun setPicCount() {
        binding.tvPicCount.text = getString(R.string.pic_count, viewModel.picture.value.size)
    }

    private fun setEnableNextButton() {
        binding.btnNext.isEnabled = true
    }

    private fun setNotEnableNextButton() {
        binding.btnNext.isEnabled = false
    }

    private fun setCheckPictureMax() {
        repeatOnStarted {
            viewModel.overPictures.collect {
                if (it) {
                    Toast.makeText(
                        binding.root.context,
                        "사진은 6개까지 추가 가능합니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    private fun setPictureObserver() {
        repeatOnStarted {
            viewModel.picture.collect {
                picAdapter.submitList(it)
                when {
                    it.size >= 3 -> setEnableNextButton()
                    else -> setNotEnableNextButton()
                }
                setPicCount()
            }
        }
    }

    private fun setNextButton() {
        binding.btnNext.setOnClickListener {
            viewModel.getImageUrl()
            findNavController().navigate(R.id.action_picChoiceFragment_to_addressSearchFragment)
            viewModel.setNextPage()
        }
    }

    private fun setBackButton() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
            viewModel.setBackPage()
        }
    }

    private fun setUploadButton() {
        binding.cvUploadPic.setOnClickListener {
            if(
                REQUIRED_PERMISSIONS.all {
                    logger("REQUIRED_PERMISSIONS : $it")
                    checkSelfPermission(requireContext(), it) == PERMISSION_GRANTED
                }
            ) {
                val intent = Intent(ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                    type = "image/*"
                }
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                albumLauncher.launch(intent)
            } else {
                requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
            }
        }
    }

    override fun removePic(position: Int) {
        viewModel.removePicUri(position)
    }

    override fun changePic(beforePosition: Int, targetPosition: Int) {
        viewModel.replacePic(beforePosition, targetPosition)
    }

    private fun drop(itemView: View, targetPosition: Int) {
        DropHelper.configureView(
            requireActivity(),
            itemView,
            arrayOf(
                ClipDescription.MIMETYPE_TEXT_PLAIN,
                "image/*",
                "application/x-arc-uri-list" // Support external items on Chrome OS Android 9
            ),
            DropHelper.Options.Builder()
                .setHighlightColor(ContextCompat.getColor(binding.root.context, R.color.lightGray))
                .build()
        ) { _, payload ->
            logger("drop : $targetPosition")
            val item = payload.clip.getItemAt(0)
            val (_, remaining) = payload.partition { it == item }

            handleImageDrop(item, targetPosition)
            remaining
        }
    }

    private fun handleImageDrop(item: ClipData.Item, targetPosition: Int) {
        val beforePosition = item.text.toString().toInt()
        viewModel.replacePic(beforePosition, targetPosition)
        logger("$beforePosition, $targetPosition")
        picAdapter.notifyItemChanged(beforePosition)
        picAdapter.notifyItemChanged(targetPosition)
//        if(beforePosition == 0 || targetPosition == 0) {
//            picAdapter.notifyItemChanged(beforePosition)
//            picAdapter.notifyItemChanged(targetPosition)
//        }
    }
}
