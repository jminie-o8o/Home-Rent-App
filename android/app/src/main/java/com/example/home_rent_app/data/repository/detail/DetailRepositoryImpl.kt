package com.example.home_rent_app.data.repository.detail

import com.example.home_rent_app.data.datasource.detailHome.DetailHomeDataSource
import com.example.home_rent_app.data.dto.toDetailHomeData
import com.example.home_rent_app.data.dto.toJWT
import com.example.home_rent_app.data.model.DetailHomeData
import com.example.home_rent_app.data.model.JWT
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailRepositoryImpl @Inject constructor(
    private val dataSource: DetailHomeDataSource
) : DetailRepository {

    override suspend fun getHomeDetail(id: Int): DetailHomeData {
        return dataSource.getDetailHome(id).toDetailHomeData()
    }

    override suspend fun refreshToken(): JWT {
        return dataSource.refreshToken().toJWT()
    }
}
