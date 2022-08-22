package com.example.home_rent_app.di

import com.example.home_rent_app.data.repository.findroom.FindRoomRepository
import com.example.home_rent_app.data.repository.findroom.FindRoomRepositoryImpl
import com.example.home_rent_app.data.repository.transfer.TransferRepository
import com.example.home_rent_app.data.repository.transfer.TransferRepositoryImpl
import com.example.home_rent_app.data.repository.wanthomeresult.WantHomeResultRepository
import com.example.home_rent_app.data.repository.wanthomeresult.WantHomeResultRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindTransferRepository(
        transferRepositoryImpl: TransferRepositoryImpl
    ): TransferRepository

    @Singleton
    @Binds
    abstract fun bindWantHomeResultRepository(
        wantHomeResultRepositoryImpl: WantHomeResultRepositoryImpl
    ): WantHomeResultRepository

    @Singleton
    @Binds
    abstract fun bindRoomFindRepository(
        findRoomRepositoryImpl: FindRoomRepositoryImpl
    ) : FindRoomRepository
}
