package com.example.home_rent_app.data.repository.login

import com.example.home_rent_app.data.model.KakaoOauthRequest
import com.example.home_rent_app.data.model.OAuthTokenResponse

interface LoginRepository {

    suspend fun getKakaoToken(kakaoOauthRequest: KakaoOauthRequest): OAuthTokenResponse
}
