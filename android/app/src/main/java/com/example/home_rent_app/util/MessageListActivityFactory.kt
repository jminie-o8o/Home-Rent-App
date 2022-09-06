package com.example.home_rent_app.util

import android.content.Context
import com.example.home_rent_app.ui.chatting.RentMessageListActivity
import com.example.home_rent_app.ui.chatting.WantedMessageListActivity
import io.getstream.chat.android.client.models.Channel

const val RENT = "rent"
const val WANTED = "wanted"

class MessageListActivityFactory() {
    companion object {
        fun create(context: Context, channel: Channel) {
            when (channel.extraData["homeType"].toString()) {
                RENT -> context.startActivity(RentMessageListActivity.newIntent(context, channel))
                WANTED -> context.startActivity(WantedMessageListActivity.newIntent(context, channel))
            }
        }
    }
}
