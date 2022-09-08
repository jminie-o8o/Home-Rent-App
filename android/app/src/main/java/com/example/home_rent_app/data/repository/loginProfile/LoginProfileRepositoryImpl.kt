package com.example.home_rent_app.data.repository.loginProfile

import com.example.home_rent_app.data.datasource.loginprofile.LoginProfileDataSource
import com.example.home_rent_app.data.dto.toNickNameCheck
import com.example.home_rent_app.data.dto.toUserData
import com.example.home_rent_app.data.model.NickNameCheck
import com.example.home_rent_app.data.model.UserProfileRequest
import com.example.home_rent_app.data.session.UserSession
import javax.inject.Inject

class LoginProfileRepositoryImpl @Inject constructor(
    private val loginProfileDataSource: LoginProfileDataSource,
    private val userSession: UserSession
) : LoginProfileRepository {

    override suspend fun checkNickName(nickName: String): NickNameCheck {
        return loginProfileDataSource.checkNickName(nickName).toNickNameCheck()
    }

    override suspend fun setUserProfile(userProfileRequest: UserProfileRequest) {
        loginProfileDataSource.setUserProfile(userSession.userId ?: 0, userProfileRequest)
    }

    override suspend fun connectUserInfo(userId: Int, connectChat: (String, String) -> Unit) {
        val userInfo = loginProfileDataSource.getUserInfo(userId).toUserData()
        connectChat(userInfo.displayName, userInfo.profileImageUrl)
    }
}
