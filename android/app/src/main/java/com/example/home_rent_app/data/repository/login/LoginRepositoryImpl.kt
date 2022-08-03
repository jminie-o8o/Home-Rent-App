package com.example.home_rent_app.data.repository.login

import com.example.home_rent_app.data.api.LoginApi
import com.example.home_rent_app.data.model.KakaoOauthRequest
import com.example.home_rent_app.data.model.OAuthTokenResponse

class LoginRepositoryImpl(private val loginApi: LoginApi): LoginRepository {

    override suspend fun getKakaoToken(kakaoOauthRequest: KakaoOauthRequest): OAuthTokenResponse {
        return loginApi.getKakaoToken(kakaoOauthRequest)
    }
}
