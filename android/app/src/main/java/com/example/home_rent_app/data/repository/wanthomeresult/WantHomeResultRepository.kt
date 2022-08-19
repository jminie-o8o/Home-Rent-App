package com.example.home_rent_app.data.repository.wanthomeresult

import com.example.home_rent_app.data.dto.WantHomeResultDTO
import kotlinx.coroutines.flow.Flow

interface WantHomeResultRepository {

    fun getResult(): Flow<List<WantHomeResultDTO>>
}
