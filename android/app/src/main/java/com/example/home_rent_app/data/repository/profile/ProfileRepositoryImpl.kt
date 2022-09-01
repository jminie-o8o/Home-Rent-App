package com.example.home_rent_app.data.repository.profile

import com.example.home_rent_app.data.api.ProfileApi
import com.example.home_rent_app.data.dto.DeleteGiveHomeResponseDTO
import com.example.home_rent_app.data.dto.DeleteWantHomeResponseDTO
import com.example.home_rent_app.data.dto.GiveHomeProfileDTO
import com.example.home_rent_app.data.dto.WantHomeProfileDTO
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val api: ProfileApi): ProfileRepository {

    override suspend fun getGiveHomeProfileResult(userId: Int, page: Int): GiveHomeProfileDTO {
        return api.getGiveHomeProfileResult(userId, page)
    }

    override suspend fun getWantHomeProfileResult(userId: Int, page: Int): WantHomeProfileDTO {
        return api.getWantHomeProfileResult(userId, page)
    }

    override suspend fun delete(id: Int): DeleteGiveHomeResponseDTO {
        return api.deleteItem(id)
    }

    override suspend fun deleteWantHome(id: Int): DeleteWantHomeResponseDTO {
        return api.deleteWantItem(id)
    }
}
