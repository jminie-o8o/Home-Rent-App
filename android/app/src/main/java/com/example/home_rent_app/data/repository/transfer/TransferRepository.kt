package com.example.home_rent_app.data.repository.transfer

import com.example.home_rent_app.data.model.ImageUrl
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface TransferRepository {

    fun getImageUrl(list : List<MultipartBody.Part>): Flow<ImageUrl>
}