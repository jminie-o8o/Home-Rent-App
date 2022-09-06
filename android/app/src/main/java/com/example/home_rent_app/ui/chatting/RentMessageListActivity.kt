package com.example.home_rent_app.ui.chatting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.home_rent_app.databinding.ActivityMessageListRentBinding
import com.example.home_rent_app.ui.viewmodel.DetailHomeViewModel
import com.example.home_rent_app.util.UiState
import com.example.home_rent_app.util.logger
import com.example.home_rent_app.util.repeatOnStarted
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

@OptIn(ExperimentalStreamChatApi::class)
@AndroidEntryPoint
class RentMessageListActivity : AppCompatActivity(), MessageListActivity {

    private val binding: ActivityMessageListRentBinding by lazy {
        ActivityMessageListRentBinding.inflate(layoutInflater)
    }

    private lateinit var cid: String

    private lateinit var factory: MessageListViewModelFactory

    private var homeId: Int? = null

    private val messageListViewModel: MessageListViewModel by viewModels { factory }
    private val messageComposerViewModel: MessageComposerViewModel by viewModels { factory }

    private val detailHomeViewModel: DetailHomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        cid = checkNotNull(intent.getStringExtra(CID_KEY)) {
            "MessageListActivity를 시작하기 위해서는 채널 아이디 (cid) 정보가 필요합니다."
        }
        intent?.getStringExtra("homeId")?.let {
            homeId = it.toInt()
        }

        logger("RentMessageListActivity : $homeId")

        factory = MessageListViewModelFactory(cid)

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

        repeatOnStarted {
            messageListViewModel.channelState.collect {
                it?.messages?.collect { list ->
                    if (list.isNotEmpty()) {
                        binding.cloInstanceMessage.visibility = View.GONE
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

        repeatOnStarted {
            detailHomeViewModel.detailHomeData.collect {
                when (it) {
                    is UiState.Success -> {
                        binding.home = it.data
                    }
                    is UiState.Error -> {
                        Toast.makeText(this@RentMessageListActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> logger("detailHomeData Loading...")
                }
            }
        }

        detailHomeViewModel.getDetailHomeData(requireNotNull(homeId))
    }

    companion object {
        // MessageListActivity의 인텐트 생성 및 채팅방의 cid 정보 전달
        private const val CID_KEY = "key:cid"

        fun newIntent(context: Context, channel: Channel): Intent {
            logger("newIntent ${channel.id}")
            return Intent(context, RentMessageListActivity::class.java)
                .putExtra(CID_KEY, channel.cid)
                .putExtra("homeType", channel.extraData["homeType"].toString())
                .putExtra("homeId", channel.extraData["homeId"].toString())
        }
    }
}
