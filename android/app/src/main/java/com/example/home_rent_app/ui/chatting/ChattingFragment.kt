package com.example.home_rent_app.ui.chatting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.home_rent_app.BuildConfig
import com.example.home_rent_app.databinding.FragmentChattingBinding
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.offline.plugin.configuration.Config
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory

class ChattingFragment : Fragment() {

    private val binding: FragmentChattingBinding by lazy {
        FragmentChattingBinding.inflate(layoutInflater)
    }

    private lateinit var client: ChatClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val offlinePluginFactory = StreamOfflinePluginFactory(
            config = Config(),
            appContext = requireContext().applicationContext
        )

        client = ChatClient.Builder(BuildConfig.streamStreamSdkKey, requireContext().applicationContext)
            .withPlugin(offlinePluginFactory)
            .logLevel(ChatLogLevel.ALL)
            .build()
    }
}
