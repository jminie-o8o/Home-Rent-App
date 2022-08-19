package com.example.home_rent_app.ui.findroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.home_rent_app.databinding.ActivityFindRoomBinding
import com.example.home_rent_app.ui.viewmodel.FindRoomViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindRoomActivity : AppCompatActivity() {

    private val binding: ActivityFindRoomBinding by lazy {
        ActivityFindRoomBinding.inflate(layoutInflater)
    }

    private val viewModel: FindRoomViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickNavigationButton()
    }

    private fun setClickNavigationButton() {
        binding.abTopAppbar.setNavigationOnClickListener {
            finish()
        }
    }
}