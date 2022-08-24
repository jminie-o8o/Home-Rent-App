package com.example.home_rent_app.data.repository.loginProfile

import com.example.home_rent_app.data.api.LoginProfileApi
import com.example.home_rent_app.data.dto.toImageUrl
import com.example.home_rent_app.data.dto.toNickNameCheck
import com.example.home_rent_app.data.model.ImageUrl
import com.example.home_rent_app.data.model.NickNameCheck
import com.example.home_rent_app.data.model.UserProfileRequest
import com.example.home_rent_app.util.UserSession
import com.example.home_rent_app.util.logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class LoginProfileRepositoryImpl @Inject constructor(
    private val api: LoginProfileApi,
    private val userSession: UserSession
) : LoginProfileRepository {

    override suspend fun checkNickName(nickName: String): NickNameCheck {
        return api.checkNickName(nickName).toNickNameCheck()
    }

    override fun getImageUrl(body: List<MultipartBody.Part>): Flow<ImageUrl> {
        return flow {
            emit(api.getImageUrl(body).toImageUrl())
        }
    }

    override suspend fun setUserProfile(userProfileRequest: UserProfileRequest) {
        logger("UserProfile : ${userSession.user?.userId}")
        logger("UserProfile : $userProfileRequest")
        api.setUserProfile(userSession.user?.userId, userProfileRequest)
    }
}
