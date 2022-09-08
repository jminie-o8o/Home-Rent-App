package com.example.home_rent_app.util

import androidx.annotation.WorkerThread
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.utils.onErrorSuspend
import io.getstream.chat.android.client.utils.onSuccessSuspend
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatChannel @Inject constructor(
    private val chatClient: ChatClient,
) {
    @WorkerThread
    fun joinNewChannel(
        homeType: String,
        userId: String,
        chatUserID: String,
        homeId: String,
        profileImage: String,
    ) = flow {
        val result = chatClient.createChannel(
            channelType = "messaging",
            channelId = "$homeId-$homeType",
            memberIds = listOf(userId, chatUserID),
            extraData = mapOf(
                "homeType" to homeType,
                "homeId" to homeId,
                "image" to profileImage
            ) // userId 필드 생기면 수정하기
        ).await()

        result.onSuccessSuspend {
            logger("성공 ${it.id}")
            emit(it)
        }
        result.onErrorSuspend {
            logger("실패 ${it.message}")
        }
    }
}
