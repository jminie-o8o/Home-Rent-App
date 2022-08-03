package com.example.home_rent_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.model.KakaoOauthRequest
import kotlinx.coroutines.launch

class LoginViewModel(): ViewModel() {

    fun getKakaoToken(kakaoOauthRequest: KakaoOauthRequest) {
        viewModelScope.launch {

        }
    }
}
