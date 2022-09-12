package com.example.home_rent_app.data.datasource.imageurl

import com.example.home_rent_app.data.model.ImageUrl
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ImageUrlDataSource {

    fun getImageUrl(list: List<MultipartBody.Part>): Flow<ImageUrl>
}
