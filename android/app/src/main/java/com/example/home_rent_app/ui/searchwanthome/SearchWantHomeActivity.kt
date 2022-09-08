package com.example.home_rent_app.ui.searchwanthome

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.home_rent_app.R
import com.example.home_rent_app.data.model.WantHomeResultRequest
import com.example.home_rent_app.databinding.ActivitySearchWantHomeBinding
import com.example.home_rent_app.ui.detail.DetailRentActivity
import com.example.home_rent_app.ui.searchwanthome.viewmodel.SearchWantHomeViewModel
import com.example.home_rent_app.ui.wanthome.detail.WantHomeDetailActivity
import com.example.home_rent_app.util.UiState
import com.example.home_rent_app.util.collectLatestStateFlow
import com.example.home_rent_app.util.collectStateFlow
import com.example.home_rent_app.util.logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException

@AndroidEntryPoint
class SearchWantHomeActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchWantHomeBinding
    private val viewModel: SearchWantHomeViewModel by viewModels()
    lateinit var adapter: WantHomeResultAdapter

    private val geToDetail: (Int) -> Unit = {
        // 상세화면 이동
        val intent = Intent(this, WantHomeDetailActivity::class.java)
        intent.putExtra("homeId", it)
        startActivity(intent)
    }

    private val addBookmark: (Int) -> Unit = {
        viewModel.addBookmark(it)
    }

    private val deleteBookmark: (Int) -> Unit = {
        viewModel.deleteBookmark(it)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_want_home)
        binding.lifecycleOwner = this
        handleSearchWord()
        setDefaultResult()
        adapter = WantHomeResultAdapter(geToDetail, addBookmark, deleteBookmark)
        handlePagingError(adapter)
        binding.rvWanthomeResult.adapter = adapter
        updateAdapter()
        addBookMarkToast()
        deleteBookMarkToast()
        goBack()
        observeError()
        lifecycleScope.launch {
        }
    }

    private fun handleSearchWord() {
        binding.etWantHome.addTextChangedListener { text ->
            if (text != null) viewModel.handleSearchWork(text.toString())
            logger("로그 : ${text.toString()}")
        }
    }

    private fun updateAdapter() {
        collectLatestStateFlow(viewModel.wantHomeResult) { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    adapter.submitData(uiState.data)
                }
                is UiState.Error -> {
                    Toast.makeText(this, uiState.message, Toast.LENGTH_SHORT).show()
                }
                else -> logger("Loading...")
            }
        }
    }

    private fun observeError() {
        collectStateFlow(viewModel.error) {
            Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setDefaultResult() {
        collectStateFlow(viewModel.searchWord) { keyword ->
            logger("로그 keyword: ${keyword}")
            viewModel.getWantHomeResult(
                WantHomeResultRequest(
                    keyword,
                    true
                )
            )
        }
    }

    private fun addBookMarkToast() {
        collectStateFlow(viewModel.addBookmarkStatusCode) { code ->
            if (code == 200) Toast.makeText(this, "관심목록에 추가되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteBookMarkToast() {
        collectStateFlow(viewModel.deleteBookmarkStatusCode) { code ->
            if (code == 200) Toast.makeText(this, "관심목록에서 제거되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goBack() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun handlePagingError(wantHomeResultAdapter: WantHomeResultAdapter) {

        wantHomeResultAdapter.addLoadStateListener { loadState ->
            val errorState = when {
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            when (val throwable = errorState?.error) {
                is HttpException -> {
                    if (throwable.code() == 401) {
                        Toast.makeText(this, "401 에러입니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Http에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                is ConnectException -> {
                    Toast.makeText(this, "네트워크 연결이 불안정합니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
