package com.example.home_rent_app.data.repository.refresh

import com.example.home_rent_app.data.datasource.refresh.RefreshDataSource
import com.example.home_rent_app.data.dto.toJWT
import com.example.home_rent_app.data.model.JWT
import com.example.home_rent_app.util.logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshRepositoryImpl @Inject constructor(
    private val dataSource: RefreshDataSource
) : RefreshRepository {

    override suspend fun refreshToken(): JWT {
        logger("RefreshRepositoryImpl refreshToken")
        return dataSource.refreshAuthToken().toJWT()
    }

}