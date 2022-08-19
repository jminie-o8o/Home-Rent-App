package com.example.home_rent_app.ui.findroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.home_rent_app.databinding.ActivityFindRoomBinding
import com.example.home_rent_app.ui.viewmodel.FindRoomViewModel
import com.example.home_rent_app.util.UiState
import com.example.home_rent_app.util.logger
import com.example.home_rent_app.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@AndroidEntryPoint
class FindRoomActivity : AppCompatActivity() {

    private val binding: ActivityFindRoomBinding by lazy {
        ActivityFindRoomBinding.inflate(layoutInflater)
    }

    private val viewModel: FindRoomViewModel by viewModels()

    private val adapter: TempAdapter = TempAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickNavigationButton()
        setSearchResult()
        search()
        binding.rvSearchResult.adapter = adapter
        binding.vm = viewModel
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun search() {
        repeatOnStarted {
            viewModel.searchAddress.collect {
                logger("${it}")
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