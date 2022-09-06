package com.example.home_rent_app.data.repository.refresh

import com.example.home_rent_app.data.datasource.refresh.RefreshDataSource
import com.example.home_rent_app.data.dto.toJWT
import com.example.home_rent_app.data.model.JWT
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshRepositoryImpl @Inject constructor(
    private val dataSource: RefreshDataSource
) : RefreshRepository {

    override suspend fun refreshToken(): JWT? {
        val response = dataSource.refreshAuthToken()
        return if (response == null) {
            null
        } else {
            dataSource.refreshAuthToken()?.toJWT()
        }
    }
}
