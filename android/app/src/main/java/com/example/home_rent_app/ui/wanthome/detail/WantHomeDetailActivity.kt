package com.example.home_rent_app.ui.wanthome.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.ActivityWantHomeDetailBinding
import com.example.home_rent_app.ui.chatting.WantedMessageListActivity
import com.example.home_rent_app.ui.wanthome.viewmodel.WantHomeViewModel
import com.example.home_rent_app.util.collectStateFlow
import com.example.home_rent_app.util.logger
import com.example.home_rent_app.util.repeatOnStarted
import com.example.home_rent_app.util.setLikeClickEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch

@AndroidEntryPoint
class WantHomeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWantHomeDetailBinding
    private val viewModel: WantHomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_want_home_detail)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        goHomeActivity()
        clickLikeButton()
        getWantHomeDetail()
        checkMyItem()
        setGoChat()
    }

    private fun goHomeActivity() {
        binding.btnGoToHome.setOnClickListener {
            onBackPressed()
        }
    }

    private fun clickLikeButton() {
        binding.btnLike.setLikeClickEvent(lifecycleScope) {
            binding.btnLike.isSelected = binding.btnLike.isSelected != true
        }
    }

    private fun getWantHomeDetail() {
        val id = intent?.getIntExtra("homeId", -1)
        if (id != null) {
            viewModel.getWantHomeDetail(id)
        }
    }

    private fun checkMyItem() {
        collectStateFlow(viewModel.wantHomeDetail) { Response ->
            if (Response?.user?.userId == viewModel.getUserIdFromUserSession()) {
                binding.btnLike.visibility = View.GONE
                binding.btnGoChat.visibility = View.GONE
            }
        }
    }

    private fun setGoChat() {
        binding.btnGoChat.setOnClickListener {
            logger("btnGoChat")
            repeatOnStarted {
                viewModel.joinNewChannel()
                    .catch { e ->
                        logger("chat error : ${e.message}")
                    }.collect {
                        startActivity(
                            WantedMessageListActivity.newIntent(
                                this@WantHomeDetailActivity,
                                it
                            )
                        )
                        finish()
                    }
            }
        }
    }
}
