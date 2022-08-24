package com.example.home_rent_app.data.datasource.findroom

import com.example.home_rent_app.data.api.FindRoomApi
import com.example.home_rent_app.data.api.TokenRefreshApi
import com.example.home_rent_app.data.dto.OAuthTokenResponse
import com.example.home_rent_app.data.dto.RoomSearchResultDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FindRoomDataSourceImpl @Inject constructor(
    private val api: FindRoomApi,
    private val refreshApi: TokenRefreshApi
) : FindRoomDataSource {

    override fun getSearchResult(
        page: Int,
        size: Int,
        availableOnly: Boolean,
        sortedBy: String,
        searchAddress: String
    ) = flow {
        emit(api.getSearchResult(page, size, availableOnly, sortedBy, searchAddress))
    }.flowOn(Dispatchers.IO)

    override fun refreshAuthToken(): Flow<OAuthTokenResponse> = flow {
        emit(refreshApi.getAuthToken())
    }.flowOn(Dispatchers.IO)
}
