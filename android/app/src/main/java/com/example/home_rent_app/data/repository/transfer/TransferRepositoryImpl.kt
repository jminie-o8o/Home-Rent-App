package com.example.home_rent_app.data.repository.transfer

import com.example.home_rent_app.data.datasource.transfer.TransferDataSource
import com.example.home_rent_app.data.dto.AddRentHomeRequest
import com.example.home_rent_app.data.dto.toImageUrl
import kotlinx.coroutines.flow.mapNotNull
import okhttp3.MultipartBody
import javax.inject.Inject

class TransferRepositoryImpl @Inject constructor(
    private val transferDataSource: TransferDataSource
) : TransferRepository {

    override fun getImageUrl(list: List<MultipartBody.Part>) =
        transferDataSource.getImageUrl(list).mapNotNull { it.toImageUrl() }

    override suspend fun addRentHome(request: AddRentHomeRequest) =
        transferDataSource.addRentHome(request)
}
