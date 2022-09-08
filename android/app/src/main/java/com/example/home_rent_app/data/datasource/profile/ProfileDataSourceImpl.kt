package com.example.home_rent_app.data.datasource.profile

import com.example.home_rent_app.data.api.LogoutApi
import com.example.home_rent_app.data.api.ProfileApi
import com.example.home_rent_app.data.datastore.DataStore
import com.example.home_rent_app.data.dto.DeleteGiveHomeResponseDTO
import com.example.home_rent_app.data.dto.DeleteWantHomeResponseDTO
import com.example.home_rent_app.data.dto.GetUserInfoDTO
import com.example.home_rent_app.data.dto.GiveHomeProfileDTO
import com.example.home_rent_app.data.dto.LogoutResponseDTO
import com.example.home_rent_app.data.dto.NickNameCheckDTO
import com.example.home_rent_app.data.dto.WantHomeProfileDTO
import com.example.home_rent_app.data.model.UserProfileRequest
import javax.inject.Inject

class ProfileDataSourceImpl @Inject constructor(
    private val api: ProfileApi,
    private val logoutApi: LogoutApi,
    private val dataStore: DataStore
): ProfileDataSource {

    override suspend fun getUserInfo(userId: Int): GetUserInfoDTO {
        return api.getUserInfo(userId)
    }

    override suspend fun getGiveHomeProfileResult(userId: Int, page: Int): GiveHomeProfileDTO {
        return api.getGiveHomeProfileResult(userId, page)

    }

    override suspend fun getWantHomeProfileResult(userId: Int, page: Int): WantHomeProfileDTO {
        return api.getWantHomeProfileResult(userId, page)
    }

    override suspend fun deleteRentHome(id: Int): DeleteGiveHomeResponseDTO {
        return api.deleteItem(id)
    }

    override suspend fun deleteWantHome(id: Int): DeleteWantHomeResponseDTO {
        return api.deleteWantItem(id)
    }

    override suspend fun checkNickName(nickName: String): NickNameCheckDTO {
        return api.checkNickName(nickName)
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
