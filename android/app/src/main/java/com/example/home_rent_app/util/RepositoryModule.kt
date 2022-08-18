package com.example.home_rent_app.util

import com.example.home_rent_app.data.repository.login.LoginRepository
import com.example.home_rent_app.data.repository.login.LoginRepositoryImpl
import com.example.home_rent_app.data.repository.loginProfile.LoginProfileRepository
import com.example.home_rent_app.data.repository.loginProfile.LoginProfileRepositoryImpl
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
    abstract fun bindLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl
    ): LoginRepository

    @Singleton
    @Binds
    abstract fun bindLoginProfileRepository(
        loginProfileRepositoryImpl: LoginProfileRepositoryImpl
    ): LoginProfileRepository
}
