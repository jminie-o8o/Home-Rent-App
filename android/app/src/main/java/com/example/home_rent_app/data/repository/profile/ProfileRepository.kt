package com.example.home_rent_app.data.repository.profile

import com.example.home_rent_app.data.dto.DeleteGiveHomeResponseDTO
import com.example.home_rent_app.data.dto.DeleteWantHomeResponseDTO
import com.example.home_rent_app.data.dto.GiveHomeProfileDTO
import com.example.home_rent_app.data.dto.WantHomeProfileDTO

interface ProfileRepository {

    suspend fun getGiveHomeProfileResult(userId: Int, page: Int): GiveHomeProfileDTO

    suspend fun getWantHomeProfileResult(userId: Int, page: Int): WantHomeProfileDTO

    suspend fun delete(id: Int): DeleteGiveHomeResponseDTO

    suspend fun deleteWantHome(id: Int): DeleteWantHomeResponseDTO
}
