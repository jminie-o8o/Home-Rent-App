package com.example.home_rent_app.data.datasource.login

import com.example.home_rent_app.data.api.LoginApi
import com.example.home_rent_app.data.datastore.DataStore
import com.example.home_rent_app.data.dto.OAuthTokenResponse
import com.example.home_rent_app.data.model.KakaoOauthRequest
import com.example.home_rent_app.data.model.NaverOauthRequest
import com.example.home_rent_app.data.model.User
import com.example.home_rent_app.ui.HomeActivity
import com.example.home_rent_app.util.UserSession
import com.example.home_rent_app.util.logger
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.utils.onErrorSuspend
import io.getstream.chat.android.client.utils.onSuccessSuspend
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginDataSourceImpl @Inject constructor(
    private val loginApi: LoginApi,
    private val userSession: UserSession,
    private val chatClient: ChatClient,
    private val dataStore: DataStore
) : LoginDataSource{

    override suspend fun getKakaoToken(kakaoOauthRequest: KakaoOauthRequest): OAuthTokenResponse {
        return loginApi.getKakaoToken(kakaoOauthRequest)
    }

    override suspend fun getNaverToken(naverOauthRequest: NaverOauthRequest): OAuthTokenResponse {
        return loginApi.getNaverToken(naverOauthRequest)
    }

//    override suspend fun getKakaoUser(kakaoOauthRequest: KakaoOauthRequest): User {
//        return loginApi.getKakaoToken(kakaoOauthRequest).toUser()
//    }
//
//    override suspend fun getNaverUser(naverOauthRequest: NaverOauthRequest): User {
//        return loginApi.getNaverToken(naverOauthRequest).toUser()
//    }

    override suspend fun saveIsLogin() {
        // AccessToken, RefreshToken 이 제대로 들어온 여부를 확인하는 boolean 값
        dataStore.saveIsLogin()
    }

    override suspend fun getIsLogin(): Flow<Boolean> {
        return dataStore.getIsLogin()
    }

    override suspend fun saveUserID(userId: Int?) {
        userId?.let {
            dataStore.saveUserID(it)
        }
    }

    override suspend fun saveDisplayName(displayName: String?) {
        if (!displayName.isNullOrEmpty()) {
            dataStore.saveDisplayName(displayName)
        }
    }

    override suspend fun saveProfileImage(image: String?) {
        if (!image.isNullOrEmpty()) {
            dataStore.saveProfileImage(image)
        }
    }

    override suspend fun saveGender(gender: String?) {
        if (!gender.isNullOrEmpty()) {
            dataStore.saveGender(gender)
        }
    }

    override fun getUserId() = dataStore.getUserId()

    override fun getDisplayName() = dataStore.getDisplayName()

    override fun getProfileImage() = dataStore.getProfileImage()

    override fun getGender() = dataStore.getGender()

//    override suspend fun getUserInfo(): User {
//        return dataStore.getUserInfo()
//    }

    override suspend fun setUserSession(user: User) {
        userSession.user = user
    }

    override fun connectUser() = flow {
        // disconnect a user if already connected.
        disconnectUser()
        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMSJ9.ibSL8_561JIGQxkRvz3IBZiDQ24iE5YHoOkzyN2Bg9g"

        val result = chatClient.connectUser(HomeActivity.user, token).await()

        result.onSuccessSuspend {
            emit(result.data())
        }
        result.onErrorSuspend {
            logger("Connect fail : ${it.message}")
        }
    }

    private fun disconnectUser() {
        val currentUser = chatClient.getCurrentUser()
        if (currentUser != null && HomeActivity.user.id == currentUser.id) {
            chatClient.disconnect(true).execute()
        }
    }
}