package com.example.home_rent_app.ui.chatting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.home_rent_app.databinding.FragmentChattingBinding
import com.example.home_rent_app.util.MessageListActivityFactory
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModel
import io.getstream.chat.android.ui.channel.list.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.factory.ChannelListViewModelFactory

@AndroidEntryPoint
class ChattingFragment : Fragment() {

    private val binding: FragmentChattingBinding by lazy {
        FragmentChattingBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = ChannelListViewModelFactory()

        val viewModel: ChannelListViewModel by viewModels { viewModelFactory }
        viewModel.bindView(binding.channelListView, this)

        setOnChannelClick()
    }

    private fun setOnChannelClick() {
        binding.channelListView.setChannelItemClickListener { channel ->
            MessageListActivityFactory.create(requireContext(), channel)
        }
    }
}
