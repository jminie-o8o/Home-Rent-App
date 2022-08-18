package com.example.home_rent_app.data.datasource.transfer

import com.example.home_rent_app.data.api.TransferApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject

class TransferDataSourceImpl @Inject constructor(private val api: TransferApi) : TransferDataSource {

    override fun getImageUrl(list : List<MultipartBody.Part>) = flow {
        emit(api.getImageUrl(list))
    }.flowOn(Dispatchers.IO)

}
