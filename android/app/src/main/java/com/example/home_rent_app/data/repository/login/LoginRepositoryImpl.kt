package com.example.home_rent_app.data.repository.login

import com.example.home_rent_app.data.datasource.login.LoginDataSource
import com.example.home_rent_app.data.dto.OAuthTokenResponse
import com.example.home_rent_app.data.model.KakaoOauthRequest
import com.example.home_rent_app.data.model.NaverOauthRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
) : LoginRepository {

    override suspend fun getKakaoToken(kakaoOauthRequest: KakaoOauthRequest): OAuthTokenResponse {
        return loginDataSource.getKakaoToken(kakaoOauthRequest)
    }

    override suspend fun getNaverToken(naverOauthRequest: NaverOauthRequest): OAuthTokenResponse {
        return loginDataSource.getNaverToken(naverOauthRequest)
    }

    override suspend fun saveIsLogin() {
        loginDataSource.saveIsLogin()
    }

    override suspend fun getIsLogin(): Flow<Boolean> {
        return loginDataSource.getIsLogin()
    }

    override suspend fun saveUserIDAtDataStore(userId: Int?) {
        loginDataSource.saveUserIDAtDataStore(userId)
    }

    override suspend fun saveDisplayName(displayName: String?) {
        loginDataSource.saveDisplayName(displayName)
    }

    override suspend fun saveProfileImage(image: String?) {
        loginDataSource.saveProfileImage(image)
    }

    override suspend fun saveGender(gender: String?) {
        loginDataSource.saveGender(gender)
    }

    override fun getUserId() = loginDataSource.getUserId()

    override fun getDisplayName() = loginDataSource.getDisplayName()

    override fun getProfileImage() = loginDataSource.getProfileImage()

    override fun getGender() = loginDataSource.getGender()

    override suspend fun setUserIdAtUserSession(userId: Int) {
        loginDataSource.setUserIdAtUserSession(userId)
    }

    override fun connectUser(name: String, image: String) = loginDataSource.connectUser(name, image)
}
