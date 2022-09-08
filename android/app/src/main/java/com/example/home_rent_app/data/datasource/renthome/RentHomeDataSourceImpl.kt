package com.example.home_rent_app.data.datasource.renthome

import com.example.home_rent_app.data.api.AddRentHomeApi
import com.example.home_rent_app.data.dto.AddRentHomeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject

class RentHomeDataSourceImpl @Inject constructor(
    private val api: AddRentHomeApi
) : RentHomeDataSource {

    override suspend fun addRentHome(request: AddRentHomeRequest) = api.addRentHome(request)
}
