package com.example.home_rent_app.data.datasource.renthome

import com.example.home_rent_app.data.api.AddRentHomeApi
import com.example.home_rent_app.data.dto.AddRentHomeRequest
import javax.inject.Inject

class RentHomeDataSourceImpl @Inject constructor(
    private val api: AddRentHomeApi
) : RentHomeDataSource {

    override suspend fun addRentHome(request: AddRentHomeRequest) = api.addRentHome(request)
}
