package com.example.home_rent_app.data.repository.login

import com.example.home_rent_app.data.model.KakaoOauthRequest
import com.example.home_rent_app.data.model.OAuthTokenResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun getKakaoToken(kakaoOauthRequest: KakaoOauthRequest): OAuthTokenResponse

    // DataStore
    suspend fun saveToken(token: List<String>)

    suspend fun getToken(): Flow<List<String>>
}
