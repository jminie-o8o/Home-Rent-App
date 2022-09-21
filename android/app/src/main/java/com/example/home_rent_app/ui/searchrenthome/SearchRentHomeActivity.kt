package com.example.home_rent_app.ui.searchrenthome

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.home_rent_app.databinding.ActivitySearchRentHomeBinding
import com.example.home_rent_app.ui.detail.DetailRentActivity
import com.example.home_rent_app.ui.searchrenthome.adapter.RentHomeListAdapter
import com.example.home_rent_app.ui.searchrenthome.viewmodel.SearchRentHomeViewModel
import com.example.home_rent_app.util.UiState
import com.example.home_rent_app.util.logger
import com.example.home_rent_app.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchRentHomeActivity : AppCompatActivity() {

    private val binding: ActivitySearchRentHomeBinding by lazy {
        ActivitySearchRentHomeBinding.inflate(layoutInflater)
    }

    private val viewModel: SearchRentHomeViewModel by viewModels()

    private val geToDetail: (Int) -> Unit = {
        // 상세화면 이동
        val intent = Intent(this, DetailRentActivity::class.java)
        intent.putExtra("homeId", it)
        startActivity(intent)
    }

    private val addBookmark: (Int) -> Unit = {
        viewModel.addBookmark(it)
    }

    private val deleteBookmark: (Int) -> Unit = {
        viewModel.deleteBookmark(it)
    }

    private val adapter: RentHomeListAdapter = RentHomeListAdapter(geToDetail, addBookmark, deleteBookmark)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickNavigationButton()
        setSearchResult()
        search()
        setBookmarkEventObserve()
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
                when (uiState) {
                    is UiState.Success -> {
                        adapter.submitList(uiState.data.rentArticles)
                    }
                    is UiState.Error -> {
                        Toast.makeText(binding.root.context, "데이터를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
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

    private fun setBookmarkEventObserve() {
        repeatOnStarted {
            viewModel.bookmarkEvent.collect {
                Toast.makeText(binding.root.context, "관심목록에 ${it}되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
