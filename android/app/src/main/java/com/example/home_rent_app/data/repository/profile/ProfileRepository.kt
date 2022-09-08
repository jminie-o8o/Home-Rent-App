package com.example.home_rent_app.data.repository.profile

import com.example.home_rent_app.data.dto.DeleteGiveHomeResponseDTO
import com.example.home_rent_app.data.dto.DeleteWantHomeResponseDTO
import com.example.home_rent_app.data.dto.GetUserInfoDTO
import com.example.home_rent_app.data.dto.GiveHomeProfileDTO
import com.example.home_rent_app.data.dto.LogoutResponseDTO
import com.example.home_rent_app.data.dto.WantHomeProfileDTO
import com.example.home_rent_app.data.model.NickNameCheck
import com.example.home_rent_app.data.model.UserProfileRequest

interface ProfileRepository {

    suspend fun getUserInfo(): GetUserInfoDTO

    suspend fun getGiveHomeProfileResult(page: Int): GiveHomeProfileDTO

    suspend fun getWantHomeProfileResult(page: Int): WantHomeProfileDTO

    suspend fun deleteRentHome(id: Int): DeleteGiveHomeResponseDTO

    suspend fun deleteWantHome(id: Int): DeleteWantHomeResponseDTO

    suspend fun checkNickName(nickName: String): NickNameCheck

    suspend fun setUserProfile(userProfileRequest: UserProfileRequest)

    suspend fun logout(): LogoutResponseDTO

    suspend fun clearDataStore()
}
