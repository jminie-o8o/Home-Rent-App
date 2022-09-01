package com.example.home_rent_app.ui.chatting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.home_rent_app.databinding.ActivityMessageListWantedBinding
import com.example.home_rent_app.ui.viewmodel.DetailHomeViewModel
import com.getstream.sdk.chat.viewmodel.messages.MessageListViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.common.state.Edit
import io.getstream.chat.android.common.state.MessageMode
import io.getstream.chat.android.common.state.Reply
import io.getstream.chat.android.core.ExperimentalStreamChatApi
import io.getstream.chat.android.ui.message.composer.viewmodel.MessageComposerViewModel
import io.getstream.chat.android.ui.message.composer.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.factory.MessageListViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalStreamChatApi::class)
@AndroidEntryPoint
class WantedMessageListActivity : AppCompatActivity(), MessageListActivity {

    private val binding: ActivityMessageListWantedBinding by lazy {
        ActivityMessageListWantedBinding.inflate(layoutInflater)
    }

    private val cid: String = checkNotNull(intent.getStringExtra(CID_KEY)) {
        "MessageListActivity를 시작하기 위해서는 채널 아이디 (cid) 정보가 필요합니다."
    }

    private val factory: MessageListViewModelFactory by lazy {
        MessageListViewModelFactory(cid)
    }

    private val homeId = intent?.getIntExtra("homeId", -1)

    private val messageListViewModel: MessageListViewModel by viewModels { factory }

    private val messageComposerViewModel: MessageComposerViewModel by viewModels { factory }

    private val detailHomeViewModel: DetailHomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        val factory = MessageListViewModelFactory(cid)
//        val messageListViewModel: MessageListViewModel by viewModels { factory }
//        val messageComposerViewModel: MessageComposerViewModel by viewModels { factory }

        // Step 2 - 채팅방 컴포넌트에 뷰모델 연결
        messageListViewModel.bindView(binding.messageListView, this)
        messageComposerViewModel.bindView(binding.messageComposerView, this)

        // Step 3 - MessageListHeaderView와 MessageInputView 연결 및 채팅 쓰레드 모드 업데이트
        messageListViewModel.mode.observe(this) { mode ->
            when (mode) {
                is MessageListViewModel.Mode.Thread -> {
                    messageComposerViewModel.setMessageMode(MessageMode.MessageThread(mode.parentMessage))
                }
                is MessageListViewModel.Mode.Normal -> {
                    messageComposerViewModel.leaveThread()
                }
            }
        }

        // Step 4 - 채팅방 동작에 대한 이벤트 구독 및 NavigateUp 이벤트 발생 시 채팅방 종료
        messageListViewModel.state.observe(this) { state ->
            if (state is MessageListViewModel.State.NavigateUp) {
                finish()
            }
        }

        // Step 5 - 채팅방 헤더의 뒤로가기 버튼 및 디바이스 백버튼 터치 시 NavigateUp 이벤트 송출
        val backHandler = {
            messageListViewModel.onEvent(MessageListViewModel.Event.BackButtonPressed)
        }

        binding.messageListHeaderView.setNavigationOnClickListener {
            backHandler()
        }
        onBackPressedDispatcher.addCallback(this) {
            backHandler()
        }

        binding.messageListView.setMessageReplyHandler { _, message ->
            messageComposerViewModel.performMessageAction(Reply(message))
        }
        binding.messageListView.setMessageEditHandler { message ->
            messageComposerViewModel.performMessageAction(Edit(message))
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                messageListViewModel.channelState.collect {
                    it?.messages?.collect { list ->
                        if(list.isNotEmpty()) {
                            binding.cloInstanceMessage.visibility = View.GONE
                        }
                    }
                }
            }
        }
        binding.messageComposerView.setLeadingContent(CustomMessageComposerLeadingContent(this))

        binding.messageComposerView.setLeadingContent(
            CustomMessageComposerLeadingContent(this).also {
                it.cameraButtonClickListener = {
                    binding.messageComposerView.attachmentsButtonClickListener()
                }
            }
        )

        binding.tvSampleMessageOne.setOnClickListener { message ->
            message as TextView
            messageComposerViewModel.sendMessage(messageComposerViewModel.buildNewMessage(message.text.toString()))
        }

        binding.tvSampleMessageTwo.setOnClickListener { message ->
            message as TextView
            messageComposerViewModel.sendMessage(messageComposerViewModel.buildNewMessage(message.text.toString()))
        }

        detailHomeViewModel.getDetailHomeData(requireNotNull(homeId))

    }

//    private fun set

    companion object {
        // MessageListActivity의 인텐트 생성 및 채팅방의 cid 정보 전달
        private const val CID_KEY = "key:cid"

        fun newIntent(context: Context, channel: Channel): Intent =
            Intent(context, RentMessageListActivity::class.java)
                .putExtra(CID_KEY, channel.cid)
                .putExtra("homeType", channel.extraData["homeType"].toString())
                .putExtra("homeId", channel.extraData["homeId"].toString())

    }


}
