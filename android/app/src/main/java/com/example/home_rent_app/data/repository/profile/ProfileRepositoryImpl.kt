package com.example.home_rent_app.data.repository.profile

import com.example.home_rent_app.data.api.LogoutApi
import com.example.home_rent_app.data.api.ProfileApi
import com.example.home_rent_app.data.datastore.DataStore
import com.example.home_rent_app.data.dto.DeleteGiveHomeResponseDTO
import com.example.home_rent_app.data.dto.DeleteWantHomeResponseDTO
import com.example.home_rent_app.data.dto.GetUserInfoDTO
import com.example.home_rent_app.data.dto.GiveHomeProfileDTO
import com.example.home_rent_app.data.dto.LogoutResponseDTO
import com.example.home_rent_app.data.dto.WantHomeProfileDTO
import com.example.home_rent_app.data.dto.toImageUrl
import com.example.home_rent_app.data.dto.toNickNameCheck
import com.example.home_rent_app.data.model.ImageUrl
import com.example.home_rent_app.data.model.NickNameCheck
import com.example.home_rent_app.data.model.UserProfileRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: ProfileApi,
    private val logoutApi: LogoutApi,
    private val dataStore: DataStore
) : ProfileRepository {

    override suspend fun getUserInfo(userId: Int): GetUserInfoDTO {
        return api.getUserInfo(userId)
    }

    override suspend fun getGiveHomeProfileResult(userId: Int, page: Int): GiveHomeProfileDTO {
        return api.getGiveHomeProfileResult(userId, page)
    }

    override suspend fun getWantHomeProfileResult(userId: Int, page: Int): WantHomeProfileDTO {
        return api.getWantHomeProfileResult(userId, page)
    }

    override suspend fun delete(id: Int): DeleteGiveHomeResponseDTO {
        return api.deleteItem(id)
    }

    override suspend fun deleteWantHome(id: Int): DeleteWantHomeResponseDTO {
        return api.deleteWantItem(id)
    }

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

    override suspend fun logout(): LogoutResponseDTO {
        return logoutApi.logout()
    }

    override suspend fun clearDataStore() {
        dataStore.clearDataStore()
    }
}
