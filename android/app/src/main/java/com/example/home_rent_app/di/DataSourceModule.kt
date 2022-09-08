package com.example.home_rent_app.di

import com.example.home_rent_app.data.datasource.detailHome.DetailHomeDataSource
import com.example.home_rent_app.data.datasource.detailHome.DetailHomeDataSourceImpl
import com.example.home_rent_app.data.datasource.findhome.FindHomeDataSource
import com.example.home_rent_app.data.datasource.findhome.FindHomeDataSourceImpl
import com.example.home_rent_app.data.datasource.login.LoginDataSource
import com.example.home_rent_app.data.datasource.login.LoginDataSourceImpl
import com.example.home_rent_app.data.datasource.map.MapDataSource
import com.example.home_rent_app.data.datasource.map.MapDataSourceImpl
import com.example.home_rent_app.data.datasource.refresh.RefreshDataSource
import com.example.home_rent_app.data.datasource.refresh.RefreshDataSourceImpl
import com.example.home_rent_app.data.datasource.renthome.RentHomeDataSource
import com.example.home_rent_app.data.datasource.renthome.RentHomeDataSourceImpl
import com.example.home_rent_app.data.datasource.token.TokenDataSource
import com.example.home_rent_app.data.datasource.token.TokenDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindTransferRDataSource(
        transferDataSourceImpl: RentHomeDataSourceImpl
    ): RentHomeDataSource

    @Singleton
    @Binds
    abstract fun bindRoomFindDataSource(
        findRoomDataSourceImpl: FindHomeDataSourceImpl
    ): FindHomeDataSource

    @Singleton
    @Binds
    abstract fun bindDetailHomeDataSource(
        detailHomeDataSourceImpl: DetailHomeDataSourceImpl
    ): DetailHomeDataSource

    @Singleton
    @Binds
    abstract fun bindRefreshDataSource(
        refreshDataSourceImpl: RefreshDataSourceImpl
    ): RefreshDataSource

    @Singleton
    @Binds
    abstract fun bindTokenDataSource(
        tokenDataSourceImpl: TokenDataSourceImpl
    ): TokenDataSource

    @Singleton
    @Binds
    abstract fun bindLoginDataSource(
        loginDataSourceImpl: LoginDataSourceImpl
    ): LoginDataSource

    @Singleton
    @Binds
    abstract fun bindMapDataSource(
        mapDataSourceImpl: MapDataSourceImpl
    ): MapDataSource
}
