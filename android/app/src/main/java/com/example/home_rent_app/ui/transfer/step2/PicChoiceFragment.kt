package com.example.home_rent_app.ui.transfer.step2

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.loader.content.CursorLoader
import androidx.recyclerview.widget.GridLayoutManager
import com.example.home_rent_app.databinding.FragmentPicChoiceBinding
import com.example.home_rent_app.ui.transfer.TransferViewModel
import com.example.home_rent_app.util.PicControlListener
import com.example.home_rent_app.util.logger
import com.example.home_rent_app.util.repeatOnStarted
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class PicChoiceFragment : Fragment(), PicControlListener {

    private val binding: FragmentPicChoiceBinding by lazy {
        FragmentPicChoiceBinding.inflate(layoutInflater)
    }

    private val viewModel: TransferViewModel by activityViewModels()

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
                    logger("clipData : ${clipData.toString()}")
                    if (clipData == null) { //이미지를 하나만 선택할 경우 clipData가 null이 올수 있음
                        val selectedImageUri = it?.data?.data ?: Uri.EMPTY

                        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
                        val c: Cursor? = context?.contentResolver?.query(selectedImageUri, proj, null, null, null)
                        val index = c?.getColumnIndexOrThrow("_data") ?: -1
                        val result = if(c!!.moveToFirst()) {
                            c.getString(index)
                        } else {
                            null
                        }
                        
                        logger("result : $result selectedImageUri : $selectedImageUri index: $index selectedImageUri.path : ${selectedImageUri.path}")

                        logger("result : $result selectedImageUri : $selectedImageUri uri : $ index: $index")
                        val uri = selectedImageUri.path.orEmpty()
                        val file = File(uri)
                        logger("file.name : ${file.name}")
                        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                        val body = MultipartBody.Part.createFormData("proFile", file.name, requestFile)

                        viewModel.setPictureUri(selectedImageUri)
                    } else {
                        clipData.let { data ->
                            for (i in 0 until clipDataSize!!) { //선택 한 사진수만큼 반복
                                val selectedImageUri = data.getItemAt(i).uri ?: Uri.EMPTY

                                val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
                                val c: Cursor? = context?.contentResolver?.query(selectedImageUri, proj, null, null, null)
                                val index = c?.getColumnIndexOrThrow("_data") ?: -1
                                val result = if(c!!.moveToFirst()) {
                                    c.getString(index)
                                } else {
                                    null
                                }

                                logger("result : $result selectedImageUri : $selectedImageUri uri : $ index: $index")
                                val uri = selectedImageUri.path.orEmpty()
                                val file = File(uri)
                                logger("file.name : ${file.name}")
                                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                                val body = MultipartBody.Part.createFormData("proFile", file.name, requestFile)

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

        binding.cvUploadPic.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            albumLauncher.launch(Intent.createChooser(intent, "Select Picture"))
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

    override fun removePic(position: Int) {
        viewModel.removePicUri(position)
    }

    override fun changePic(beforePosition: Int, targetPosition: Int) {
        viewModel.replacePic(beforePosition, targetPosition)
    }

}

