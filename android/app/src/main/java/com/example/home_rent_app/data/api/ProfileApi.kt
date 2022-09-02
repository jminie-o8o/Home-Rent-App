package com.example.home_rent_app.data.api

import com.example.home_rent_app.data.dto.DeleteGiveHomeResponseDTO
import com.example.home_rent_app.data.dto.DeleteWantHomeResponseDTO
import com.example.home_rent_app.data.dto.GiveHomeProfileDTO
import com.example.home_rent_app.data.dto.ImageUrlDTO
import com.example.home_rent_app.data.dto.LogoutResponseDTO
import com.example.home_rent_app.data.dto.NickNameCheckDTO
import com.example.home_rent_app.data.dto.WantHomeProfileDTO
import com.example.home_rent_app.data.model.UserProfileRequest
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileApi {

    @GET("users/{userId}/articles/rent")
    suspend fun getGiveHomeProfileResult(
        @Path("userId") userId: Int,
        @Query("page") page: Int,
        @Query("size") size: Int = 5
    ): GiveHomeProfileDTO

    @GET("users/{userId}/articles/wanted")
    suspend fun getWantHomeProfileResult(
        @Path("userId") userId: Int,
        @Query("page") page: Int,
        @Query("size") size: Int = 5
    ): WantHomeProfileDTO

    @DELETE("houses/rent/{houseId}")
    suspend fun deleteItem(
        @Path("houseId") id: Int
    ): DeleteGiveHomeResponseDTO

    @DELETE("houses/wanted/{id}")
    suspend fun deleteWantItem(
        @Path("id") id: Int
    ): DeleteWantHomeResponseDTO

    @GET("users/check-duplication")
    suspend fun checkNickName(@Query("nickname") nickName: String): NickNameCheckDTO

    @Multipart
    @POST("images")
    suspend fun getImageUrl(
        @Part images: List<MultipartBody.Part>
    ): ImageUrlDTO

    @PATCH("users/{userId}")
    suspend fun setUserProfile(
        @Path("userId") userId: Int?,
        @Body userProfileRequest: UserProfileRequest
    )
}
