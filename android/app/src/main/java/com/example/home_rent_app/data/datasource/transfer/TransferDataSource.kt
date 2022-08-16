package com.example.home_rent_app.data.datasource.transfer

import com.example.home_rent_app.data.dto.ImageUrlDTO
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface TransferDataSource {

    fun getImageUrl(list : List<MultipartBody.Part>): Flow<Unit>
}