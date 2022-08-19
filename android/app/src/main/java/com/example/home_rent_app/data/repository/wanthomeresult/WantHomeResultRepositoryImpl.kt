package com.example.home_rent_app.data.repository.wanthomeresult

import com.example.home_rent_app.data.dto.WantHomeResultDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WantHomeResultRepositoryImpl @Inject constructor() : WantHomeResultRepository {
    override fun getResult(): Flow<List<WantHomeResultDTO>> {
        TODO("Not yet implemented")
    }
}
