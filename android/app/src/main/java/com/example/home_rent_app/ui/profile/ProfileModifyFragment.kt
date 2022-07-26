package com.example.home_rent_app.ui.profile

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.home_rent_app.R
import com.example.home_rent_app.data.model.UserProfileRequest
import com.example.home_rent_app.data.session.UserSession
import com.example.home_rent_app.databinding.FragmentProfileModifyBinding
import com.example.home_rent_app.ui.home.HomeActivity
import com.example.home_rent_app.ui.profile.viewmodel.ProfileViewModel
import com.example.home_rent_app.util.Constants.REQUIRED_PERMISSIONS
import com.example.home_rent_app.util.Constants.REQ_GALLERY
import com.example.home_rent_app.util.FileController
import com.example.home_rent_app.util.collectStateFlow
import com.example.home_rent_app.util.logger
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileModifyFragment : Fragment() {

    lateinit var binding: FragmentProfileModifyBinding
    private val profileViewModel: ProfileViewModel by activityViewModels()

    @Inject
    lateinit var userSession: UserSession

    @Inject
    lateinit var fileController: FileController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile_modify, container, false)
        binding.vm = profileViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNavigation(true)
        binding.btnLoginProfileModify.setOnClickListener {
            if (isAllPermissionGranted()) {
                selectGallery()
            } else {
                requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
            }
        }
        setNickname()
        setImageUrl()
        setGender()
        checkNickname()
        addAccount()
        goBack()
        binding.btnLoginProfile.isEnabled = false
        enableButton()
    }

    override fun onDestroy() {
        super.onDestroy()
        hideBottomNavigation(false)
    }

    private fun selectGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        // intent 의 data 와 type 을 동시에 설정하는 메서드
        intent.setDataAndType(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            "image/*"
        )
        imageResult.launch(intent)
    }

    private val imageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data
                imageUri?.let {
                    logger("URI: ${fileController.uriToMultiPart(it)}")
                    profileViewModel.getProfileImage(fileController.uriToMultiPart(it))
                    binding.ivLoginProfile.load(it)
                }
            }
        }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            activity as Activity,
            REQUIRED_PERMISSIONS,
            REQ_GALLERY
        )
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            val deniedList = result.filter {
                !it.value
            }.map {
                it.key
            }

            when {
                deniedList.isNotEmpty() -> {
                    val map = deniedList.groupBy { permission ->
                        if (shouldShowRequestPermissionRationale(permission)) {
                            "DENIED"
                        } else {
                            "EXPLAINED"
                        }
                    }
                    map["DENIED"]?.let {
                        // 한번 거절했을 경우 재요청
                        Toast.makeText(
                            requireContext(),
                            "앨범에 접근하려면 권한이 필요합니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                        requestPermissions()
                    }
                    map["EXPLAINED"]?.let {
                        // 두번 거절했을 경우
                        Toast.makeText(
                            requireContext(),
                            "설정에서 미디어 접근 권한을 허용해주세요.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    // 권한 중 하나라도 허용되지 않았다면 false 를 반환하는 함수
    private fun isAllPermissionGranted(): Boolean = REQUIRED_PERMISSIONS.all { permission ->
        ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkNickname() {
        binding.btnNicknameCheck.setOnClickListener {
            binding.etLoginProfileNickname.text?.toString()
                ?.let { nickName -> profileViewModel.checkNickName(nickName) }
        }
        collectStateFlow(this.profileViewModel.nickNameCheck) { nickNameCheck ->
            if (nickNameCheck) binding.etLoginProfileNickname.error = "이미 존재하는 닉네임입니다."
            else {
                binding.etLoginProfileNickname.error = null
                Toast.makeText(requireContext(), "사용가능한 닉네임 입니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUserProfile() {
        var imageUrl = ""
        val displayName = binding.etLoginProfileNickname.text?.toString() ?: ""
        val gender = if (binding.rbMale.isChecked) "MALE"
        else "FEMALE"
        collectStateFlow(this.profileViewModel.imageUrl) {
            logger("imageUrl : $imageUrl")
            imageUrl = it
        }
        // User 정보를 서버에 보내기
        profileViewModel.setUserProfile(UserProfileRequest(displayName, imageUrl, gender))
    }

    private fun addAccount() {
        binding.btnLoginProfile.setOnClickListener {
            setUserProfile()
            val activity = activity as HomeActivity
            activity.goBack()
        }
    }

    private fun goBack() {
        binding.btnGoToBack.setOnClickListener {
            val activity = activity as HomeActivity
            activity.goBack()
        }
    }

    private fun setNickname() {
        collectStateFlow(this.profileViewModel.userData) {
            logger("userData : ${it?.displayName}")
            binding.etLoginProfileNickname.setText(it?.displayName)
        }
    }

    private fun setGender() {
        collectStateFlow(this.profileViewModel.userData) {
            when (it?.gender) {
                "MALE" -> binding.rbMale.isChecked = true
                "FEMALE" -> binding.rbFemale.isChecked = true
            }
        }
    }

    private fun setImageUrl() {
        collectStateFlow(this.profileViewModel.imageUrl) { imageUrl ->
            logger("imageUrl : $imageUrl")
            binding.ivLoginProfile.load(imageUrl)
        }
    }

    private fun enableButton() {
        collectStateFlow(this.profileViewModel.nickNameCheck) { nickNameCheck ->
            binding.btnLoginProfile.isEnabled = !nickNameCheck
        }
    }

    private fun hideBottomNavigation(boolean: Boolean) {
        val bottomNavigationView =
            (activity as HomeActivity).findViewById<BottomNavigationView>(R.id.navigation)
        if (boolean) bottomNavigationView.visibility = View.GONE
        else bottomNavigationView.visibility = View.VISIBLE
    }

//    companion object {
//        private const val REQ_GALLERY = 1
//        private val REQUIRED_PERMISSIONS = arrayOf(
//            android.Manifest.permission.READ_EXTERNAL_STORAGE,
//            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//        )
//    }
}
