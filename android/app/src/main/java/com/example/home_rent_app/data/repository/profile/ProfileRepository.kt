package com.example.home_rent_app.data.repository.profile

import com.example.home_rent_app.data.dto.DeleteHomeResponseDTO

interface ProfileRepository {

    suspend fun delete(id: Int): DeleteHomeResponseDTO
}
