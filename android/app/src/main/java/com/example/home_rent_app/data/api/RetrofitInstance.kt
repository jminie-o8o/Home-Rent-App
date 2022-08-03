package com.example.home_rent_app.data.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitInstance {

    private const val BASE_URL = "http://3.34.188.98:8080/"

    @Provides
    @Singleton
    fun loginOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()
    }

    @Provides
    @Singleton
    fun loginRetrofit(): LoginApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(loginOkHttpClient())
            .baseUrl(BASE_URL)
            .build()
            .create(LoginApi::class.java)
    }
}
