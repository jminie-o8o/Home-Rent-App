package com.example.home_rent_app.ui.chatting

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.home_rent_app.databinding.MessageCompserLeadingContentBinding
import io.getstream.chat.android.common.composer.MessageComposerState
import io.getstream.chat.android.core.ExperimentalStreamChatApi
import io.getstream.chat.android.ui.message.composer.MessageComposerContext
import io.getstream.chat.android.ui.message.composer.content.MessageComposerContent

@OptIn(ExperimentalStreamChatApi::class)
class CustomMessageComposerLeadingContent : FrameLayout, MessageComposerContent {

    var cameraButtonClickListener: () -> Unit = {}

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val binding = MessageCompserLeadingContentBinding.inflate(LayoutInflater.from(context), this, true)
        binding.camera.setOnClickListener { cameraButtonClickListener() }
    }

    @ExperimentalStreamChatApi
    override fun attachContext(messageComposerContext: MessageComposerContext) {
        messageComposerContext.style
    }

    @ExperimentalStreamChatApi
    override fun renderState(state: MessageComposerState) {

    }
}
