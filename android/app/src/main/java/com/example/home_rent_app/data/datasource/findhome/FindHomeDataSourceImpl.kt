package com.example.home_rent_app.data.datasource.findhome

import com.example.home_rent_app.data.api.SearchRentHomeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FindHomeDataSourceImpl @Inject constructor(
    private val api: SearchRentHomeApi
) : FindHomeDataSource {

    override fun getSearchResult(
        page: Int,
        size: Int,
        availableOnly: Boolean,
        sortedBy: String,
        searchAddress: String
    ) = flow {
        emit(api.searchRentHome(page, size, availableOnly, sortedBy, searchAddress))
    }.flowOn(Dispatchers.IO)
}
