package com.example.home_rent_app.data.repository.profile

import com.example.home_rent_app.data.api.ProfileApi
import com.example.home_rent_app.data.dto.DeleteHomeResponseDTO
import com.example.home_rent_app.data.dto.GiveHomeProfileDTO
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val api: ProfileApi): ProfileRepository {

    override suspend fun getGiveHomeProfileResult(userId: Int, page: Int): GiveHomeProfileDTO {
        return api.getGiveHomeProfileResult(userId, page)
    }

    override suspend fun delete(id: Int): DeleteHomeResponseDTO {
        return api.deleteItem(id)
    }
}
