package com.example.home_rent_app.data.api

import com.example.home_rent_app.data.dto.OAuthTokenResponse
import com.example.home_rent_app.data.model.KakaoOauthRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("login/oauth")
    suspend fun getKakaoToken(@Body kakaoOauthRequest: KakaoOauthRequest): OAuthTokenResponse
}
