package com.example.home_rent_app.ui.findroom

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.activity.viewModels
import com.example.home_rent_app.databinding.ActivityFindRoomBinding
import com.example.home_rent_app.ui.detail.DetailRentActivity
import com.example.home_rent_app.ui.viewmodel.FindRoomViewModel
import com.example.home_rent_app.util.UiState
import com.example.home_rent_app.util.logger
import com.example.home_rent_app.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindHomeActivity : AppCompatActivity() {

    private val binding: ActivityFindRoomBinding by lazy {
        ActivityFindRoomBinding.inflate(layoutInflater)
    }

    private val viewModel: FindRoomViewModel by viewModels()

    private val adapter: HomeListAdapter = HomeListAdapter { homeId ->
        // 상세화면 이동
        val intent = Intent(this, DetailRentActivity::class.java)
        intent.putExtra("homeId", homeId)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickNavigationButton()
        setSearchResult()
        search()
        binding.rvSearchResult.adapter = adapter
        binding.vm = viewModel
    }

    private fun search() {
        repeatOnStarted {
            viewModel.searchAddress.collect {
                logger(it)
            }
        }
    }

    private fun setSearchResult() {
        repeatOnStarted {
            viewModel.result.collect { uiState ->
                when(uiState) {
                    is UiState.Success -> {
                        adapter.submitList(uiState.data.rentArticles)
                    }
                    is UiState.Error -> {
                        Toast.makeText(binding.root.context, "데이터를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                    is UiState.Loading -> {
                        logger("search result loading....")
                    }
                    else -> logger("Loading...")
                }
            }
        }
    }

    private fun setClickNavigationButton() {
        binding.abTopAppbar.setNavigationOnClickListener {
            finish()
        }
    }
}
