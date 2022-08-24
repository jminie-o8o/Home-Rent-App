package com.example.home_rent_app.data.repository.detail

import com.example.home_rent_app.data.datasource.detailHome.DetailHomeDataSource
import com.example.home_rent_app.data.dto.toDetailHomeData
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailRepositoryImpl @Inject constructor(
    private val dataSource: DetailHomeDataSource
) : DetailRepository {

    override fun getHomeDetail(id: Int) =
        dataSource.getDetailHome(id).map {
            it.toDetailHomeData()
        }

}