package com.example.home_rent_app.data.datasource.imageurl

import com.example.home_rent_app.data.api.ImageUrlApi
import com.example.home_rent_app.data.dto.toImageUrl
import com.example.home_rent_app.data.model.ImageUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class ImageUrlDataSourceImpl @Inject constructor(
    private val api: ImageUrlApi
): ImageUrlDataSource {

    override fun getImageUrl(list: List<MultipartBody.Part>): Flow<ImageUrl> {
        return flow {
            emit(api.getImageUrl(list).toImageUrl())
        }
    }
}
