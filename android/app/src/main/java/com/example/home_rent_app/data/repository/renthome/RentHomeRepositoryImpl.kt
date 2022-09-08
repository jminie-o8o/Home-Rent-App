package com.example.home_rent_app.data.repository.renthome

import com.example.home_rent_app.data.datasource.renthome.RentHomeDataSource
import com.example.home_rent_app.data.dto.AddRentHomeRequest
import com.example.home_rent_app.data.dto.toImageUrl
import kotlinx.coroutines.flow.mapNotNull
import okhttp3.MultipartBody
import javax.inject.Inject

class RentHomeRepositoryImpl @Inject constructor(
    private val rentHomeDataSource: RentHomeDataSource
) : RentHomeRepository {

    override fun getImageUrl(list: List<MultipartBody.Part>) =
        rentHomeDataSource.getImageUrl(list).mapNotNull { it.toImageUrl() }

    override suspend fun addRentHome(request: AddRentHomeRequest) =
        rentHomeDataSource.addRentHome(request)
}
