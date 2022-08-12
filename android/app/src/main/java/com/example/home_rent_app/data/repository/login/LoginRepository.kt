package com.example.home_rent_app.data.repository.login

import com.example.home_rent_app.data.model.JWT
import com.example.home_rent_app.data.model.KakaoOauthRequest
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun getKakaoToken(kakaoOauthRequest: KakaoOauthRequest): JWT

    // DataStore
    suspend fun saveToken(token: List<String>)

    suspend fun getToken(): Flow<List<String>>

    suspend fun getIsLogin(): Flow<Boolean>
}
