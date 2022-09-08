package com.example.home_rent_app.data.repository.loginProfile

import com.example.home_rent_app.data.api.LoginProfileApi
import com.example.home_rent_app.data.dto.toImageUrl
import com.example.home_rent_app.data.dto.toNickNameCheck
import com.example.home_rent_app.data.dto.toUserData
import com.example.home_rent_app.data.model.ImageUrl
import com.example.home_rent_app.data.model.NickNameCheck
import com.example.home_rent_app.data.model.UserProfileRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class LoginProfileRepositoryImpl @Inject constructor(
    private val api: LoginProfileApi
) : LoginProfileRepository {

    override suspend fun checkNickName(nickName: String): NickNameCheck {
        return api.checkNickName(nickName).toNickNameCheck()
    }

    override fun getImageUrl(body: List<MultipartBody.Part>): Flow<ImageUrl> {
        return flow {
            emit(api.getImageUrl(body).toImageUrl())
        }
    }

    override suspend fun setUserProfile(userId: Int, userProfileRequest: UserProfileRequest) {
        api.setUserProfile(userId, userProfileRequest)
    }

    override suspend fun getUserInfo(userId: Int, callback: (String, String) -> Unit) {
        val userInfo = api.getUserInfo(userId).toUserData()
        callback(userInfo.displayName, userInfo.profileImageUrl)
    }

}
