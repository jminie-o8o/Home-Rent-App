package com.example.home_rent_app.di

import com.example.home_rent_app.data.repository.bookmark.BookmarkRepository
import com.example.home_rent_app.data.repository.bookmark.BookmarkRepositoryImpl
import com.example.home_rent_app.data.repository.detail.DetailRepository
import com.example.home_rent_app.data.repository.detail.DetailRepositoryImpl
import com.example.home_rent_app.data.repository.imageurl.ImageUrlRepository
import com.example.home_rent_app.data.repository.imageurl.ImageUrlRepositoryImpl
import com.example.home_rent_app.data.repository.login.LoginRepository
import com.example.home_rent_app.data.repository.login.LoginRepositoryImpl
import com.example.home_rent_app.data.repository.loginProfile.LoginProfileRepository
import com.example.home_rent_app.data.repository.loginProfile.LoginProfileRepositoryImpl
import com.example.home_rent_app.data.repository.map.MapRepository
import com.example.home_rent_app.data.repository.map.MapRepositoryImpl
import com.example.home_rent_app.data.repository.profile.ProfileRepository
import com.example.home_rent_app.data.repository.profile.ProfileRepositoryImpl
import com.example.home_rent_app.data.repository.refresh.RefreshRepository
import com.example.home_rent_app.data.repository.refresh.RefreshRepositoryImpl
import com.example.home_rent_app.data.repository.renthome.RentHomeRepository
import com.example.home_rent_app.data.repository.renthome.RentHomeRepositoryImpl
import com.example.home_rent_app.data.repository.searchrenthome.SearchRentHomeRepository
import com.example.home_rent_app.data.repository.searchrenthome.SearchRentHomeRepositoryImpl
import com.example.home_rent_app.data.repository.searchwanthome.SearchWantHomeRepository
import com.example.home_rent_app.data.repository.searchwanthome.SearchWantHomeRepositoryImpl
import com.example.home_rent_app.data.repository.token.TokenRepository
import com.example.home_rent_app.data.repository.token.TokenRepositoryImpl
import com.example.home_rent_app.data.repository.wanthome.WantHomeRepository
import com.example.home_rent_app.data.repository.wanthome.WantHomeRepositoryImpl
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

    @Singleton
    @Binds
    abstract fun bindTransferRepository(
        transferRepositoryImpl: RentHomeRepositoryImpl
    ): RentHomeRepository

    @Singleton
    @Binds
    abstract fun bindWantHomeResultRepository(
        wantHomeResultRepositoryImpl: SearchWantHomeRepositoryImpl
    ): SearchWantHomeRepository

    @Singleton
    @Binds
    abstract fun bindRoomFindRepository(
        findRoomRepositoryImpl: SearchRentHomeRepositoryImpl
    ): SearchRentHomeRepository

    @Singleton
    @Binds
    abstract fun bindDetailHomeRepository(
        detailRepositoryImpl: DetailRepositoryImpl
    ): DetailRepository

    @Singleton
    @Binds
    abstract fun bindRefreshRepository(
        refreshRepositoryImpl: RefreshRepositoryImpl
    ): RefreshRepository

    @Singleton
    @Binds
    abstract fun bindTokenRepository(
        tokenRepositoryImpl: TokenRepositoryImpl
    ): TokenRepository

    @Singleton
    @Binds
    abstract fun bindWantHomeRepository(
        wantHomeRepositoryImpl: WantHomeRepositoryImpl
    ): WantHomeRepository

    @Singleton
    @Binds
    abstract fun bindMapRepository(
        mapRepositoryImpl: MapRepositoryImpl
    ): MapRepository

    @Singleton
    @Binds
    abstract fun bindWantHomeBookmarkRepository(
        bookmarkRepositoryImpl: BookmarkRepositoryImpl
    ): BookmarkRepository

    @Singleton
    @Binds
    abstract fun bindWantHomeProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository

    @Singleton
    @Binds
    abstract fun bindImageUrlRepository(
        imageUrlRepositoryImpl: ImageUrlRepositoryImpl
    ): ImageUrlRepository
}
