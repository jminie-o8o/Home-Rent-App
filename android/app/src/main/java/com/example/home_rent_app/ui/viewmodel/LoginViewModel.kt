package com.example.home_rent_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.dto.toJWT
import com.example.home_rent_app.data.dto.toUser
import com.example.home_rent_app.data.model.KakaoOauthRequest
import com.example.home_rent_app.data.model.NaverOauthRequest
import com.example.home_rent_app.data.repository.login.LoginRepository
import com.example.home_rent_app.util.Constants.GENDER_DEFAULT
import com.example.home_rent_app.util.logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _isLogin = MutableStateFlow(false)
    val isLogin: StateFlow<Boolean> get() = _isLogin

    private val _gender = MutableStateFlow<String?>(GENDER_DEFAULT)
    val gender: StateFlow<String?> get() = _gender

    init {
        viewModelScope.launch {
            loginRepository.getIsLogin().collect { isLogin ->
                _isLogin.value = isLogin
                logger("isLogin : $isLogin")
                if (isLogin) {
                    loginRepository.getToken().collect {
                        loginRepository.setAppSession(it)
                        loginRepository.connectUser().collect { data ->
                            logger("loginRepository.connectUser() : ${data.user}")
                        } // 채팅 로그인
                    }
                }
            }
        }
    }

    fun getKakaoToken(kakaoOauthRequest: KakaoOauthRequest) {
        viewModelScope.launch {
            val response = loginRepository.getKakaoToken(kakaoOauthRequest)
            val jwt = response.toJWT()
            val user = response.toUser()
            launch {
                loginRepository.saveToken(
                    listOf(
                        jwt.accessToken.tokenCode,
                        jwt.refreshToken.tokenCode
                    )
                )
                loginRepository.getToken().collect {
                    loginRepository.setAppSession(it)
                }
            }
            launch {
                _gender.value = user.gender
                loginRepository.saveUserID(user.userId)
                loginRepository.saveDisplayName(user.displayName)
                loginRepository.saveProfileImage(user.profileImageUrl)
                loginRepository.saveGender(user.gender)
                loginRepository.setUserSession(loginRepository.getUserInfo())
            }
        }
    }

    fun getNaverToken(naverOauthRequest: NaverOauthRequest) {
        viewModelScope.launch {
            val response = loginRepository.getNaverToken(naverOauthRequest)
            val jwt = response.toJWT()
            val user = response.toUser()
            launch {
                loginRepository.saveToken(
                    listOf(
                        jwt.accessToken.tokenCode,
                        jwt.refreshToken.tokenCode
                    )
                )
                loginRepository.getToken().collect {
                    loginRepository.setAppSession(it)
                }
            }
            launch {
                _gender.value = user.gender
                loginRepository.saveUserID(user.userId)
                loginRepository.saveDisplayName(user.displayName)
                loginRepository.saveProfileImage(user.profileImageUrl)
                loginRepository.saveGender(user.gender)
                loginRepository.setUserSession(loginRepository.getUserInfo())
            }
        }
    }

    fun saveIsLogin() {
        viewModelScope.launch {
            loginRepository.saveIsLogin()
        }
    }
}
