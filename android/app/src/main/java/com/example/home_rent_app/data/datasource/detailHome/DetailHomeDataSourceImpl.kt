package com.example.home_rent_app.data.datasource.detailHome

import com.example.home_rent_app.data.api.DetailHomeApi
import com.example.home_rent_app.data.dto.DetailHomeDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailHomeDataSourceImpl @Inject constructor(
    private val  api: DetailHomeApi
): DetailHomeDataSource {

    override fun getDetailHome(id: Int) = flow {
        emit(api.getDetailHomeDTO(id))
    }.flowOn(Dispatchers.IO)

}