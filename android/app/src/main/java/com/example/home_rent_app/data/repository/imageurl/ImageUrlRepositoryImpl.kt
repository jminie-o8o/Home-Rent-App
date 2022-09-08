package com.example.home_rent_app.data.repository.imageurl

import com.example.home_rent_app.data.datasource.imageurl.ImageUrlDataSource
import com.example.home_rent_app.data.model.ImageUrl
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class ImageUrlRepositoryImpl @Inject constructor(
    private val imageUrlDataSource: ImageUrlDataSource
) : ImageUrlRepository {

    override fun getImageUrl(list: List<MultipartBody.Part>): Flow<ImageUrl> {
        return imageUrlDataSource.getImageUrl(list)
    }
}
