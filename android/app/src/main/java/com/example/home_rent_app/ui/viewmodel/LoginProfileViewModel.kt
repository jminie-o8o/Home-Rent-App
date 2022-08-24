package com.example.home_rent_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.model.UserProfileRequest
import com.example.home_rent_app.data.repository.loginProfile.LoginProfileRepository
import com.example.home_rent_app.util.logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class LoginProfileViewModel @Inject constructor(
    private val loginProfileRepository: LoginProfileRepository
) : ViewModel() {

    private val _nickNameCheck: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val nickNameCheck: SharedFlow<Boolean> get() = _nickNameCheck

    private val _imageUrl: MutableStateFlow<String> = MutableStateFlow("")
    val imageUrl: StateFlow<String> get() = _imageUrl

    fun checkNickName(nickName: String) {
        viewModelScope.launch {
            _nickNameCheck.emit(loginProfileRepository.checkNickName(nickName).isDuplicated)
        }
    }

    fun getProfileImage(body: MultipartBody.Part) {
        viewModelScope.launch {
            val bodyList = mutableListOf<MultipartBody.Part>().apply { this.add(body) }
            loginProfileRepository.getImageUrl(bodyList).collect {
                _imageUrl.value = it.images.first()
                logger("imageUrl : ${it.images.first()}")
            }
        }
    }

    fun setUserProfile(userProfileRequest: UserProfileRequest) {
        viewModelScope.launch {
            loginProfileRepository.setUserProfile(userProfileRequest)
        }
    }
}
