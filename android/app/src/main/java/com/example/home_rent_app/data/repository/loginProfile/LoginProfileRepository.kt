package com.example.home_rent_app.data.repository.loginProfile

import com.example.home_rent_app.data.model.NickNameCheck
import com.example.home_rent_app.data.model.UserProfileRequest

interface LoginProfileRepository {

    suspend fun checkNickName(nickName: String): NickNameCheck

    suspend fun setUserProfile(userProfileRequest: UserProfileRequest)

    suspend fun connectUserInfo(userId: Int, connectChat: (String, String) -> Unit)
}
