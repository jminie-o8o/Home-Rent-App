package com.example.home_rent_app.data.api

import com.example.home_rent_app.data.dto.NickNameCheckDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginProfileApi {

    @GET("users/check-duplication")
    suspend fun checkNickName(@Query("nickname") nickName: String): NickNameCheckDTO
}
