package com.example.home_rent_app.data.repository.renthome

import com.example.home_rent_app.data.datasource.renthome.RentHomeDataSource
import com.example.home_rent_app.data.dto.AddRentHomeRequest
import javax.inject.Inject

class RentHomeRepositoryImpl @Inject constructor(
    private val rentHomeDataSource: RentHomeDataSource
) : RentHomeRepository {

    override suspend fun addRentHome(request: AddRentHomeRequest) =
        rentHomeDataSource.addRentHome(request)
}
