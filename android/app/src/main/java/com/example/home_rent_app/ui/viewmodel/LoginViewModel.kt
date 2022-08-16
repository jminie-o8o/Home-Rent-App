package com.example.home_rent_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.model.KakaoOauthRequest
import com.example.home_rent_app.data.model.NaverOauthRequest
import com.example.home_rent_app.data.repository.login.LoginRepository
import com.example.home_rent_app.util.logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

    init {
        viewModelScope.launch {
            loginRepository.getIsLogin().collect { isLogin ->
                _isLogin.value = isLogin
                logger("isLogin : $isLogin")
                if(isLogin) {
                    loginRepository.getToken().collect {
                        loginRepository.setAppSession(it)
                    }
                }
            }
        }
    }

    fun getKakaoToken(kakaoOauthRequest: KakaoOauthRequest) {
        viewModelScope.launch {
            val response = loginRepository.getKakaoToken(kakaoOauthRequest)
            _accessToken.value = response.accessToken.tokenCode
            loginRepository.saveToken(
                listOf(
                    response.accessToken.tokenCode,
                    response.refreshToken.tokenCode
                )
            )
            val token = loginRepository.getToken().last()
            loginRepository.setAppSession(token)
        }
    }

    fun getNaverToken(naverOauthRequest: NaverOauthRequest) {
        viewModelScope.launch {
            val response = loginRepository.getNaverToken(naverOauthRequest)
            _accessToken.value = response.accessToken.tokenCode
            loginRepository.saveToken(
                listOf(
                    response.accessToken.tokenCode,
                    response.accessToken.tokenCode
                )
            )
            val token = loginRepository.getToken().last()
            loginRepository.setAppSession(token)
        }
    }
}
