/*
 * Copyright 2021 Stream.IO, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.home_rent_app.initializer

import android.content.Context
import android.content.Intent
import androidx.startup.Initializer
import com.example.home_rent_app.BuildConfig
import com.example.home_rent_app.ui.LoginActivity
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.notifications.handler.NotificationConfig
import io.getstream.chat.android.client.notifications.handler.NotificationHandler
import io.getstream.chat.android.client.notifications.handler.NotificationHandlerFactory
import io.getstream.chat.android.offline.plugin.configuration.Config
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory

class StreamChatInitializer : Initializer<Unit> {

    override fun create(context: Context) {

        val logLevel = if (BuildConfig.DEBUG) ChatLogLevel.ALL else ChatLogLevel.NOTHING
        val offlinePluginFactory = StreamOfflinePluginFactory(
            config = Config(),
            appContext = context
        )

        ChatClient.Builder(BuildConfig.streamStreamSdkKey, context)
            .notifications(createNotificationConfig(), createNotificationHandler(context))
            .withPlugin(offlinePluginFactory)
            .logLevel(logLevel)
            .build()
    }

    private fun createNotificationConfig(): NotificationConfig {
        return NotificationConfig(
            pushDeviceGenerators = listOf()
        )
    }

    private fun createNotificationHandler(
        context: Context
    ): NotificationHandler {
        return NotificationHandlerFactory.createNotificationHandler(
            context = context,
            newMessageIntent = { _: String, _: String, _: String ->
                Intent(context, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            }
        )
    }

    override fun dependencies(): List<Class<out Initializer<*>>> =
        emptyList()
}
