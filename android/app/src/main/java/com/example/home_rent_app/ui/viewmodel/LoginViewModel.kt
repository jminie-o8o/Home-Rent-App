package com.example.home_rent_app.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.model.KakaoOauthRequest
import com.example.home_rent_app.data.repository.login.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _accessToken = MutableStateFlow<String?>(null)
    val accessToken: StateFlow<String?> get() = _accessToken

    fun getKakaoToken(kakaoOauthRequest: KakaoOauthRequest) {
        viewModelScope.launch {
            val response = loginRepository.getKakaoToken(kakaoOauthRequest)
            _accessToken.value = response.accessToken.tokenCode
            Log.d("AccessToken", response.accessToken.tokenCode)
            Log.d("RefreshToken", response.refreshToken.tokenCode)
            loginRepository.saveToken(
                listOf(
                    response.accessToken.tokenCode,
                    response.refreshToken.tokenCode
                )
            )
        }
    }
}
