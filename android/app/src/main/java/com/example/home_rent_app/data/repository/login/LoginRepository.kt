package com.example.home_rent_app.data.repository.login

import com.example.home_rent_app.data.model.JWT
import com.example.home_rent_app.data.model.KakaoOauthRequest
import com.example.home_rent_app.data.model.NaverOauthRequest
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun getKakaoToken(kakaoOauthRequest: KakaoOauthRequest): JWT

    suspend fun getNaverToken(naverOauthRequest: NaverOauthRequest): JWT

    // DataStore
    suspend fun saveToken(token: List<String>)

    suspend fun getToken(): Flow<List<String>>

    suspend fun saveIsLogin()

    suspend fun getIsLogin(): Flow<Boolean>

    fun setAppSession(token: List<String>)
}
