package com.example.home_rent_app.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.dto.GetUserInfoDTO
import com.example.home_rent_app.data.dto.RentArticleProfile
import com.example.home_rent_app.data.dto.WantArticleProfile
import com.example.home_rent_app.data.model.CEHModel
import com.example.home_rent_app.data.model.UserProfileRequest
import com.example.home_rent_app.data.repository.profile.ProfileRepository
import com.example.home_rent_app.util.CoroutineException
import com.example.home_rent_app.util.UserSession
import com.example.home_rent_app.util.deleteGiveProfileAtView
import com.example.home_rent_app.util.deleteWantProfileAtView
import com.example.home_rent_app.util.logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val userSession: UserSession
) : ViewModel() {

    private var page = 0

    private val _giveHomeProfileResult =
        MutableStateFlow<MutableList<RentArticleProfile>>(mutableListOf())
    val giveHomeProfileResult: StateFlow<MutableList<RentArticleProfile>> = _giveHomeProfileResult

    private val _wantHomeProfileResult =
        MutableStateFlow<MutableList<WantArticleProfile>>(mutableListOf())
    val wantHomeProfileResult: StateFlow<MutableList<WantArticleProfile>> = _wantHomeProfileResult

    private val _deleteMessage = MutableSharedFlow<String>()
    val deleteMessage: SharedFlow<String> get() = _deleteMessage

    private val _logoutMessage = MutableSharedFlow<String>()
    val logoutMessage: SharedFlow<String> get() = _logoutMessage

    private val _profileModifyMessage = MutableSharedFlow<String>()
    val profileModifyMessage: SharedFlow<String> get() = _profileModifyMessage

    private val _nickNameCheck: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val nickNameCheck: SharedFlow<Boolean> get() = _nickNameCheck

    private val _imageUrl: MutableStateFlow<String> = MutableStateFlow("")
    val imageUrl: StateFlow<String> get() = _imageUrl

    private val _userData = MutableStateFlow<GetUserInfoDTO?>(null)
    val userData: StateFlow<GetUserInfoDTO?>  = _userData

    private val _error = MutableSharedFlow<CEHModel>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val error = _error.asSharedFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _error.emit(CoroutineException.checkThrowable(throwable))
        }
    }

    init {
        getUserInfo(userSession.userId ?: 0)
        getGiveHomeProfile(userSession.userId ?: 0)
        getWantHomeProfile(userSession.userId ?: 0)
    }

    fun getUserInfo(userId: Int) {
        viewModelScope.launch(exceptionHandler) {
            val response = profileRepository.getUserInfo(userId)
            _userData.value = response
            _imageUrl.value = response.profileImageUrl
        }
    }

    fun getGiveHomeProfile(userId: Int) {
        viewModelScope.launch(exceptionHandler) {
            val response = profileRepository.getGiveHomeProfileResult(userId, page)
            if (response.hasNext) {
                return@launch
            }
            val list = mutableListOf<RentArticleProfile>()
            list.addAll(_giveHomeProfileResult.value)
            list.addAll(response.rentArticles)
            _giveHomeProfileResult.value = list
            page += 1
        }
    }

    fun getWantHomeProfile(userId: Int) {
        viewModelScope.launch(exceptionHandler) {
            val response = profileRepository.getWantHomeProfileResult(userId, page)
            if (response.hasNext) {
                return@launch
            }
            val list = mutableListOf<WantArticleProfile>()
            list.addAll(_wantHomeProfileResult.value)
            list.addAll(response.wantedArticles)
            _wantHomeProfileResult.value = list
            page += 1
        }
    }

    fun deleteGiveItem(id: Int) {
        viewModelScope.launch(exceptionHandler) {
            val response = profileRepository.delete(id)
            if (response.statusCode == 200) _deleteMessage.emit(response.message)
            _giveHomeProfileResult.deleteGiveProfileAtView(id)
        }
    }

    fun deleteWantItem(id: Int) {
        viewModelScope.launch(exceptionHandler) {
            val response = profileRepository.deleteWantHome(id)
            if (response.statusCode == 200) _deleteMessage.emit(response.message)
            _wantHomeProfileResult.deleteWantProfileAtView(id)
        }
    }

    // 닉네임 중복 검사
    fun checkNickName(nickName: String) {
        viewModelScope.launch(exceptionHandler) {
            _nickNameCheck.emit(profileRepository.checkNickName(nickName).isDuplicated)
        }
    }

    // 유저 이미지를 MultipartBody 로 보내서 Url 로 받기
    fun getProfileImage(body: MultipartBody.Part) {
        viewModelScope.launch(exceptionHandler) {
            val bodyList = mutableListOf<MultipartBody.Part>().apply { this.add(body) }
            profileRepository.getImageUrl(bodyList).collect {
                _imageUrl.value = it.images.first()
            }
        }
    }

    // 유저 정보를 서버에 보내기
    fun setUserProfile(userId: Int, userProfileRequest: UserProfileRequest) {
        viewModelScope.launch(exceptionHandler) {
            profileRepository.setUserProfile(userId, userProfileRequest)
            _deleteMessage.emit("성공적으로 프로필이 수정되었습니다.")
        }
    }

    fun logout() {
        viewModelScope.launch(exceptionHandler) {
            profileRepository.clearDataStore()
            _logoutMessage.emit(profileRepository.logout().message)
        }
    }
}
