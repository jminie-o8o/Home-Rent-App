package com.example.home_rent_app.data.repository.login

import androidx.datastore.preferences.core.Preferences
import com.example.home_rent_app.data.dto.OAuthTokenResponse
import com.example.home_rent_app.data.model.KakaoOauthRequest
import com.example.home_rent_app.data.model.NaverOauthRequest
import io.getstream.chat.android.client.models.ConnectionData
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun getKakaoToken(kakaoOauthRequest: KakaoOauthRequest): OAuthTokenResponse

    suspend fun getNaverToken(naverOauthRequest: NaverOauthRequest): OAuthTokenResponse

    // DataStore

    suspend fun saveIsLogin()

    suspend fun getIsLogin(): Flow<Boolean>

    suspend fun saveUserIDAtDataStore(userId: Int?)

    suspend fun saveDisplayName(displayName: String?)

    suspend fun saveGender(gender: String?)

//    suspend fun getUserInfo(): User

    fun getUserId(): Flow<Preferences>

    fun getDisplayName(): Flow<Preferences>

    fun getProfileImage(): Flow<Preferences>

    fun getGender(): Flow<Preferences>

    suspend fun setUserIdAtUserSession(userId: Int)

    suspend fun saveProfileImage(image: String?)

    fun connectUser(): Flow<ConnectionData>

}
