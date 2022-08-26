package com.example.home_rent_app.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import com.example.home_rent_app.databinding.ActivityDetailRentBinding
import com.example.home_rent_app.ui.chatting.RentMessageListActivity
import com.example.home_rent_app.ui.viewmodel.DetailHomeViewModel
import com.example.home_rent_app.util.UiState
import com.example.home_rent_app.util.logger
import com.example.home_rent_app.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailRentActivity : AppCompatActivity() {

    private val binding: ActivityDetailRentBinding by lazy {
        ActivityDetailRentBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailHomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val window = window
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val id = intent?.getIntExtra("homeId", -1)

        if (id != null) {
            viewModel.getDetailHomeData(1)
        }

        repeatOnStarted {
            viewModel.detailHomeData.collect {
                when(it) {
                    is UiState.Success -> {
                        binding.item = it.data
                    }
                    is UiState.Error -> {
                        Toast.makeText(binding.root.context, it.message, Toast.LENGTH_SHORT).show()
                    }
                    is UiState.Loading -> {
                        logger("Loading...")
                    }
                    else -> logger("Loading...")
                }
            }
        }

    }
}