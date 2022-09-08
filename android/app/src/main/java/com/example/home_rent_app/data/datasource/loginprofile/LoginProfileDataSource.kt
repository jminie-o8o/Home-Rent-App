package com.example.home_rent_app.data.datasource.loginprofile

import com.example.home_rent_app.data.dto.NickNameCheckDTO
import com.example.home_rent_app.data.dto.UserDTO
import com.example.home_rent_app.data.model.ImageUrl
import com.example.home_rent_app.data.model.UserProfileRequest
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface LoginProfileDataSource {

    suspend fun checkNickName(nickName: String): NickNameCheckDTO

    suspend fun setUserProfile(userId: Int, userProfileRequest: UserProfileRequest)

    suspend fun getUserInfo(userId: Int): UserDTO
}
