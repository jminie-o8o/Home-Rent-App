package com.example.home_rent_app.data.repository.profile

import com.example.home_rent_app.data.dto.DeleteHomeResponseDTO
import com.example.home_rent_app.data.dto.GiveHomeProfileDTO

interface ProfileRepository {

    suspend fun getGiveHomeProfileResult(userId: Int, page: Int): GiveHomeProfileDTO

    suspend fun delete(id: Int): DeleteHomeResponseDTO
}
