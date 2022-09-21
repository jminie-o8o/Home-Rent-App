package com.example.home_rent_app.data.repository.profile

import com.example.home_rent_app.data.datasource.profile.ProfileDataSource
import com.example.home_rent_app.data.dto.DeleteGiveHomeResponseDTO
import com.example.home_rent_app.data.dto.DeleteWantHomeResponseDTO
import com.example.home_rent_app.data.dto.GetUserInfoDTO
import com.example.home_rent_app.data.dto.GiveHomeProfileDTO
import com.example.home_rent_app.data.dto.LogoutResponseDTO
import com.example.home_rent_app.data.dto.WantHomeProfileDTO
import com.example.home_rent_app.data.dto.toNickNameCheck
import com.example.home_rent_app.data.model.NickNameCheck
import com.example.home_rent_app.data.model.UserProfileRequest
import com.example.home_rent_app.data.session.UserSession
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileDataSource: ProfileDataSource,
    private val userSession: UserSession
) : ProfileRepository {

    override suspend fun getUserInfo(): GetUserInfoDTO {
        return profileDataSource.getUserInfo(userSession.userId ?: 0)
    }

    override suspend fun getGiveHomeProfileResult(page: Int): GiveHomeProfileDTO {
        return profileDataSource.getGiveHomeProfileResult(userSession.userId ?: 0, page)
    }

    override suspend fun getWantHomeProfileResult(page: Int): WantHomeProfileDTO {
        return profileDataSource.getWantHomeProfileResult(userSession.userId ?: 0, page)
    }

    override suspend fun deleteRentHome(id: Int): DeleteGiveHomeResponseDTO {
        return profileDataSource.deleteRentHome(id)
    }

    override suspend fun deleteWantHome(id: Int): DeleteWantHomeResponseDTO {
        return profileDataSource.deleteWantHome(id)
    }

    override suspend fun checkNickName(nickName: String): NickNameCheck {
        return profileDataSource.checkNickName(nickName).toNickNameCheck()
    }

    override suspend fun setUserProfile(userProfileRequest: UserProfileRequest) {
        profileDataSource.setUserProfile(userSession.userId ?: 0, userProfileRequest)
    }

    override suspend fun logout(): LogoutResponseDTO {
        return profileDataSource.logout()
    }

    override suspend fun clearDataStore() {
        profileDataSource.clearDataStore()
    }
}
