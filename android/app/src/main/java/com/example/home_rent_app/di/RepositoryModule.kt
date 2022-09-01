package com.example.home_rent_app.di

import com.example.home_rent_app.data.repository.bookmark.BookmarkRepository
import com.example.home_rent_app.data.repository.bookmark.BookmarkRepositoryImpl
import com.example.home_rent_app.data.repository.detail.DetailRepository
import com.example.home_rent_app.data.repository.detail.DetailRepositoryImpl
import com.example.home_rent_app.data.repository.findroom.FindRoomRepository
import com.example.home_rent_app.data.repository.findroom.FindRoomRepositoryImpl
import com.example.home_rent_app.data.repository.map.MapRepository
import com.example.home_rent_app.data.repository.map.MapRepositoryImpl
import com.example.home_rent_app.data.repository.profile.ProfileRepository
import com.example.home_rent_app.data.repository.profile.ProfileRepositoryImpl
import com.example.home_rent_app.data.repository.refresh.RefreshRepository
import com.example.home_rent_app.data.repository.refresh.RefreshRepositoryImpl
import com.example.home_rent_app.data.repository.token.TokenRepository
import com.example.home_rent_app.data.repository.token.TokenRepositoryImpl
import com.example.home_rent_app.data.repository.transfer.TransferRepository
import com.example.home_rent_app.data.repository.transfer.TransferRepositoryImpl
import com.example.home_rent_app.data.repository.wanthome.WantHomeRepository
import com.example.home_rent_app.data.repository.wanthome.WantHomeRepositoryImpl
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

    @Singleton
    @Binds
    abstract fun bindDetailHomeRepository(
        detailRepositoryImpl: DetailRepositoryImpl
    ) : DetailRepository

    @Singleton
    @Binds
    abstract fun bindRefreshRepository(
        refreshRepositoryImpl: RefreshRepositoryImpl
    ) : RefreshRepository

    @Singleton
    @Binds
    abstract fun bindTokenRepository(
        tokenRepositoryImpl: TokenRepositoryImpl
    ) : TokenRepository

    @Singleton
    @Binds
    abstract fun bindWantHomeRepository(
        wantHomeRepositoryImpl: WantHomeRepositoryImpl
    ) : WantHomeRepository

    @Singleton
    @Binds
    abstract fun bindMapRepository(
        mapRepositoryImpl: MapRepositoryImpl
    ) : MapRepository

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
}

