package com.example.home_rent_app.di

import com.example.home_rent_app.data.datasource.transfer.TransferDataSource
import com.example.home_rent_app.data.datasource.transfer.TransferDataSourceImpl
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
    abstract fun bindTransferRepository(
        transferDataSourceImpl: TransferDataSourceImpl
    ) : TransferDataSource

}