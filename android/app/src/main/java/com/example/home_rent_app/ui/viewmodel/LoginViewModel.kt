package com.example.home_rent_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.model.KakaoOauthRequest
import com.example.home_rent_app.data.model.NaverOauthRequest
import com.example.home_rent_app.data.repository.login.LoginRepository
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

    private val _accessToken = MutableStateFlow<String?>(null)
    val accessToken: StateFlow<String?> get() = _accessToken

    private val _isLogin = MutableStateFlow(false)
    val isLogin: StateFlow<Boolean> get() = _isLogin

    private val _gender = MutableStateFlow<String?>(null)
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
            launch {
                val jwt = loginRepository.getKakaoToken(kakaoOauthRequest)
                _accessToken.value = jwt.accessToken.tokenCode
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
                val user = loginRepository.getKakaoUser(kakaoOauthRequest)
                _gender.value = user.gender
                loginRepository.saveUserID(user.userId)
                loginRepository.saveProfileImage(user.profileImageUrl)
            }
        }
    }

    fun getNaverToken(naverOauthRequest: NaverOauthRequest) {
        viewModelScope.launch {
            launch {
                val jwt = loginRepository.getNaverToken(naverOauthRequest)
                _accessToken.value = jwt.accessToken.tokenCode
                loginRepository.saveToken(
                    listOf(
                        jwt.accessToken.tokenCode,
                        jwt.accessToken.tokenCode
                    )
                )
                loginRepository.getToken().collect {
                    loginRepository.setAppSession(it)
                }
            }
            launch {
                val user = loginRepository.getNaverUser(naverOauthRequest)
                _gender.value = user.gender
                loginRepository.saveUserID(user.userId)
                loginRepository.saveProfileImage(user.profileImageUrl)
            }
        }
    }

    fun saveIsLogin() {
        viewModelScope.launch {
            loginRepository.saveIsLogin()
        }
    }
}
