package com.example.home_rent_app.data.repository.profile

import com.example.home_rent_app.data.api.ProfileApi
import com.example.home_rent_app.data.dto.DeleteHomeResponseDTO
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val api: ProfileApi): ProfileRepository {

    override suspend fun delete(id: Int): DeleteHomeResponseDTO {
        return api.deleteItem(id)
    }
}
