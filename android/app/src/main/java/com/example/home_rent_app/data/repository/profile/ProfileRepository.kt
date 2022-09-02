package com.example.home_rent_app.data.repository.profile

import com.example.home_rent_app.data.dto.DeleteGiveHomeResponseDTO
import com.example.home_rent_app.data.dto.DeleteWantHomeResponseDTO
import com.example.home_rent_app.data.dto.GetUserInfoDTO
import com.example.home_rent_app.data.dto.GiveHomeProfileDTO
import com.example.home_rent_app.data.dto.LogoutResponseDTO
import com.example.home_rent_app.data.dto.WantHomeProfileDTO
import com.example.home_rent_app.data.model.ImageUrl
import com.example.home_rent_app.data.model.NickNameCheck
import com.example.home_rent_app.data.model.UserProfileRequest
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ProfileRepository {

    suspend fun getUserInfo(userId: Int): GetUserInfoDTO

    suspend fun getGiveHomeProfileResult(userId: Int, page: Int): GiveHomeProfileDTO

    suspend fun getWantHomeProfileResult(userId: Int, page: Int): WantHomeProfileDTO

    suspend fun delete(id: Int): DeleteGiveHomeResponseDTO

    suspend fun deleteWantHome(id: Int): DeleteWantHomeResponseDTO

    suspend fun checkNickName(nickName: String): NickNameCheck

    fun getImageUrl(body : List<MultipartBody.Part>): Flow<ImageUrl>

    suspend fun setUserProfile(userId: Int, userProfileRequest: UserProfileRequest)

    suspend fun logout(): LogoutResponseDTO

    suspend fun clearDataStore()
}
