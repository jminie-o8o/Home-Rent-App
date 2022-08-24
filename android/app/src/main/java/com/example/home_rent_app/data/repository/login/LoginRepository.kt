package com.example.home_rent_app.data.repository.login

import com.example.home_rent_app.data.dto.OAuthTokenResponse
import com.example.home_rent_app.data.model.JWT
import com.example.home_rent_app.data.model.KakaoOauthRequest
import com.example.home_rent_app.data.model.NaverOauthRequest
import com.example.home_rent_app.data.model.User
import io.getstream.chat.android.client.models.ConnectionData
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun getKakaoToken(kakaoOauthRequest: KakaoOauthRequest): OAuthTokenResponse

    suspend fun getNaverToken(naverOauthRequest: NaverOauthRequest): OAuthTokenResponse

//    suspend fun getKakaoUser(kakaoOauthRequest: KakaoOauthRequest): User
//
//    suspend fun getNaverUser(naverOauthRequest: NaverOauthRequest): User

    // DataStore
    suspend fun saveToken(token: List<String>)

    suspend fun getToken(): Flow<List<String>>

    suspend fun saveIsLogin()

    suspend fun getIsLogin(): Flow<Boolean>

    fun setAppSession(token: List<String>)

    suspend fun saveUserID(userId: Int?)

    suspend fun saveDisplayName(displayName: String?)

    suspend fun saveProfileImage(image: String?)

    suspend fun saveGender(gender: String?)

    suspend fun getUserInfo(): User

    suspend fun setUserSession(user: User)

    fun connectUser(): Flow<ConnectionData>

}
