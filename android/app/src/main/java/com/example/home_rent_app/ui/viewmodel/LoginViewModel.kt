package com.example.home_rent_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.datastore.DataStore.PreferenceKeys.DISPLAY_NAME
import com.example.home_rent_app.data.datastore.DataStore.PreferenceKeys.GENDER
import com.example.home_rent_app.data.datastore.DataStore.PreferenceKeys.PROFILE_IMAGE
import com.example.home_rent_app.data.datastore.DataStore.PreferenceKeys.USER_ID
import com.example.home_rent_app.data.dto.toJWT
import com.example.home_rent_app.data.dto.toUser
import com.example.home_rent_app.data.model.KakaoOauthRequest
import com.example.home_rent_app.data.model.NaverOauthRequest
import com.example.home_rent_app.data.model.User
import com.example.home_rent_app.data.repository.login.LoginRepository
import com.example.home_rent_app.data.repository.token.TokenRepository
import com.example.home_rent_app.util.Constants.GENDER_DEFAULT
import com.example.home_rent_app.util.logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _isLogin = MutableStateFlow(false)
    val isLogin: StateFlow<Boolean> get() = _isLogin

    private val _gender = MutableStateFlow<String>(GENDER_DEFAULT)
    val gender: StateFlow<String?> get() = _gender

    private var userId = 0

    private var displayName = ""

    private var profileImage = ""

    init {
        viewModelScope.launch {
            loginRepository.getIsLogin().collect { isLogin ->
                _isLogin.value = isLogin
                logger("isLogin : $isLogin")
                if (isLogin) {
                    setAppSession()
                    connectUser() // 채팅 관련
                    setUserId()
                    setDisplayName()
                    setProfileImage()
                    setGender()
                    loginRepository.setUserSession(User(userId, displayName, profileImage, gender.value))
                }
            }
        }
    }

    private fun setAppSession() {
        viewModelScope.launch {
            tokenRepository.getToken().collect {
                tokenRepository.setAppSession(it)
            }
        }
    }

    private fun connectUser() {
        viewModelScope.launch {
            loginRepository.connectUser().collect { data ->
                logger("connectUser : ${data.user}") // 채팅 로그인
            }
        }
    }

    private fun setUserId() {
        viewModelScope.launch {
            loginRepository.getUserId().collect { prefs ->
                userId = prefs[USER_ID] ?: -1
            }
        }
    }

    private fun setDisplayName() {
        viewModelScope.launch {
            loginRepository.getDisplayName().collect { prefs ->
                displayName = prefs[DISPLAY_NAME].orEmpty()
            }
        }
    }

    private fun setProfileImage() {
        viewModelScope.launch {
            loginRepository.getProfileImage().collect { prefs ->
                profileImage = prefs[PROFILE_IMAGE].orEmpty()
            }
        }
    }

    private fun setGender() {
        viewModelScope.launch {
            loginRepository.getGender().collect { prefs ->
                _gender.value = prefs[GENDER].orEmpty()
            }
        }
    }

    fun getKakaoToken(kakaoOauthRequest: KakaoOauthRequest) {
        viewModelScope.launch {
            val response = loginRepository.getKakaoToken(kakaoOauthRequest)
            val jwt = response.toJWT()
            val user = response.toUser()
            launch {
                tokenRepository.saveToken(
                    listOf(
                        jwt.accessToken.tokenCode,
                        jwt.refreshToken.tokenCode
                    )
                )
                tokenRepository.getToken().collect {
                    tokenRepository.setAppSession(it)
                }
            }
            launch {
                _gender.value = user.gender.orEmpty()
                loginRepository.saveUserID(user.userId)
                loginRepository.saveDisplayName(user.displayName)
                loginRepository.saveProfileImage(user.profileImageUrl)
                loginRepository.saveGender(user.gender)
                loginRepository.setUserSession(user)
            }
        }
    }

    fun getNaverToken(naverOauthRequest: NaverOauthRequest) {
        viewModelScope.launch {
            val response = loginRepository.getNaverToken(naverOauthRequest)
            val jwt = response.toJWT()
            val user = response.toUser()
            launch {
                tokenRepository.saveToken(
                    listOf(
                        jwt.accessToken.tokenCode,
                        jwt.refreshToken.tokenCode
                    )
                )
                tokenRepository.getToken().collect {
                    tokenRepository.setAppSession(it)
                }
            }
            launch {
                _gender.value = user.gender.orEmpty()
                loginRepository.saveUserID(user.userId)
                loginRepository.saveDisplayName(user.displayName)
                loginRepository.saveProfileImage(user.profileImageUrl)
                loginRepository.saveGender(user.gender)
                loginRepository.setUserSession(user)
            }
        }
    }

    fun saveIsLogin() {
        viewModelScope.launch {
            loginRepository.saveIsLogin()
        }
    }
}
