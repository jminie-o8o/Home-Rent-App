package com.example.home_rent_app.data.api

import com.example.home_rent_app.data.dto.ImageUrlDTO
import com.example.home_rent_app.data.dto.NickNameCheckDTO
import com.example.home_rent_app.data.dto.UserDTO
import com.example.home_rent_app.data.model.UserProfileRequest
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface LoginProfileApi {

    @GET("users/check-duplication")
    suspend fun checkNickName(@Query("nickname") nickName: String): NickNameCheckDTO

    @PATCH("users/{userId}")
    suspend fun setUserProfile(
        @Path("userId") userId: Int?,
        @Body userProfileRequest: UserProfileRequest
    )

    @GET("/users/{userId}")
    suspend fun getUserInfo(
        @Path("userId") userId: Int
    ): UserDTO
}
