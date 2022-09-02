package com.example.home_rent_app.data.repository.loginProfile

import com.example.home_rent_app.data.model.ImageUrl
import com.example.home_rent_app.data.model.NickNameCheck
import com.example.home_rent_app.data.model.UserProfileRequest
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface LoginProfileRepository {

    suspend fun checkNickName(nickName: String): NickNameCheck

    fun getImageUrl(body : List<MultipartBody.Part>): Flow<ImageUrl>

    suspend fun setUserProfile(userId: Int, userProfileRequest: UserProfileRequest)
}