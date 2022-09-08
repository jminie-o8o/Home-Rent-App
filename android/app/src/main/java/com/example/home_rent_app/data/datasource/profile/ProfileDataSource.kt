package com.example.home_rent_app.data.datasource.profile

import com.example.home_rent_app.data.dto.DeleteGiveHomeResponseDTO
import com.example.home_rent_app.data.dto.DeleteWantHomeResponseDTO
import com.example.home_rent_app.data.dto.GetUserInfoDTO
import com.example.home_rent_app.data.dto.GiveHomeProfileDTO
import com.example.home_rent_app.data.dto.LogoutResponseDTO
import com.example.home_rent_app.data.dto.NickNameCheckDTO
import com.example.home_rent_app.data.dto.WantHomeProfileDTO
import com.example.home_rent_app.data.model.UserProfileRequest

interface ProfileDataSource {

    suspend fun getUserInfo(userId: Int): GetUserInfoDTO

    suspend fun getGiveHomeProfileResult(userId: Int, page: Int): GiveHomeProfileDTO

    suspend fun getWantHomeProfileResult(userId: Int, page: Int): WantHomeProfileDTO

    suspend fun deleteRentHome(id: Int): DeleteGiveHomeResponseDTO

    suspend fun deleteWantHome(id: Int): DeleteWantHomeResponseDTO

    suspend fun checkNickName(nickName: String): NickNameCheckDTO

    suspend fun setUserProfile(userId: Int, userProfileRequest: UserProfileRequest)

    suspend fun logout(): LogoutResponseDTO

    suspend fun clearDataStore()
}
