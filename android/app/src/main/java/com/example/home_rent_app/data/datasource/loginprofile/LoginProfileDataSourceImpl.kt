package com.example.home_rent_app.data.datasource.loginprofile

import com.example.home_rent_app.data.api.LoginProfileApi
import com.example.home_rent_app.data.dto.NickNameCheckDTO
import com.example.home_rent_app.data.dto.UserDTO
import com.example.home_rent_app.data.dto.toImageUrl
import com.example.home_rent_app.data.dto.toUserData
import com.example.home_rent_app.data.model.ImageUrl
import com.example.home_rent_app.data.model.UserProfileRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class LoginProfileDataSourceImpl @Inject constructor(
    private val api: LoginProfileApi
): LoginProfileDataSource {

    override suspend fun checkNickName(nickName: String): NickNameCheckDTO {
        return api.checkNickName(nickName)
    }

    override fun getImageUrl(body: List<MultipartBody.Part>): Flow<ImageUrl> {
        return flow {
            emit(api.getImageUrl(body).toImageUrl())
        }
    }

    override suspend fun setUserProfile(userId: Int, userProfileRequest: UserProfileRequest) {
        api.setUserProfile(userId, userProfileRequest)
    }

    override suspend fun getUserInfo(userId: Int): UserDTO {
        return api.getUserInfo(userId)
    }
}
