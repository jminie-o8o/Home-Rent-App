package com.example.home_rent_app.di

import androidx.annotation.WorkerThread
import com.example.home_rent_app.util.logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.getstream.chat.android.client.ChatClient
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StreamChatModule {

    @Provides
    @Singleton
    fun provideStreamChatClient(): ChatClient {
        return ChatClient.instance()
    }
}
