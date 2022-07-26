package com.example.home_rent_app.data.api

import com.example.home_rent_app.data.AuthInterceptor
import com.example.home_rent_app.data.RefreshInterceptor
import com.example.home_rent_app.util.Constants.BASE_URL
import com.example.home_rent_app.util.Constants.MAP_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitInstance {

    @Provides
    @Singleton
    @Named("login")
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
    @Named("includeRefresh")
    fun includeRefreshTokenOkHttpClient(
        refreshInterceptor: RefreshInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .addInterceptor(refreshInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named("jwt")
    fun provideJwtOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logger)
            .authenticator(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named("map")
    fun provideMapOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()
    }

    @Provides
    @Singleton
    fun loginRetrofit(
        @Named("login") okHttpClient: OkHttpClient
    ): LoginApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
            .create(LoginApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTokenRefreshApi(
        @Named("includeRefresh") okHttpClient: OkHttpClient
    ): TokenRefreshApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(TokenRefreshApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMenuListApi(
        @Named("jwt") okHttpClient: OkHttpClient
    ): AddRentHomeApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(AddRentHomeApi::class.java)
    }

    @Provides
    @Singleton
    fun retrofitWithJwt(
        @Named("jwt") okHttpClient: OkHttpClient
    ): LoginProfileApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
            .create(LoginProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun getWantHomeResultRetrofit(
        @Named("jwt") okHttpClient: OkHttpClient
    ): SearchWantHomeApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
            .create(SearchWantHomeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFindRoomApi(
        @Named("jwt") okHttpClient: OkHttpClient
    ): SearchRentHomeApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
            .create(SearchRentHomeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDetailHomeApi(
        @Named("jwt") okHttpClient: OkHttpClient
    ): DetailHomeApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
            .create(DetailHomeApi::class.java)
    }

    @Provides
    @Singleton
    fun addWantHomeApi(
        @Named("jwt") okHttpClient: OkHttpClient
    ): AddWantHomeApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
            .create(AddWantHomeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMapApi(
        @Named("map") okHttpClient: OkHttpClient
    ): MapApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(MAP_URL)
            .build()
            .create(MapApi::class.java)
    }

    @Provides
    @Singleton
    fun getWantHomeBookmark(
        @Named("jwt") okHttpClient: OkHttpClient
    ): BookmarkApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
            .create(BookmarkApi::class.java)
    }

    @Provides
    @Singleton
    fun deleteItem(
        @Named("jwt") okHttpClient: OkHttpClient
    ): ProfileApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
            .create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun logout(
        @Named("includeRefresh") okHttpClient: OkHttpClient
    ): LogoutApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
            .create(LogoutApi::class.java)
    }

    @Provides
    @Singleton
    fun retrofitWithImageUrl(
        @Named("jwt") okHttpClient: OkHttpClient
    ): ImageUrlApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
            .create(ImageUrlApi::class.java)
    }
}
