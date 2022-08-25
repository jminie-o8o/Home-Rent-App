package com.example.home_rent_app.ui.wanthomeresult

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.home_rent_app.R
import com.example.home_rent_app.data.model.WantHomeResultRequest
import com.example.home_rent_app.databinding.ActivityWantHomeResultBinding
import com.example.home_rent_app.ui.viewmodel.WantHomeResultViewModel
import com.example.home_rent_app.util.UserSession
import com.example.home_rent_app.util.collectStateFlow
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WantHomeResultActivity : AppCompatActivity() {

    lateinit var binding: ActivityWantHomeResultBinding
    private val viewModel: WantHomeResultViewModel by viewModels()
    lateinit var adapter: WantHomeResultAdapter
    @Inject lateinit var userSession: UserSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_want_home_result)
        binding.lifecycleOwner = this
        handleSearchWord()
        setAvailable()
        adapter = WantHomeResultAdapter(viewModel, userSession)
        binding.rvWanthomeResult.adapter = adapter
        updateAdapter()
        addBookMarkToast()
        deleteBookMarkToast()
    }

    private fun handleSearchWord() {
        binding.etWantHome.addTextChangedListener { text ->
            if (text != null) viewModel.handleSearchWork(text.toString())
        }
    }

    private fun updateAdapter() {
        collectStateFlow(viewModel.wantHomeResult) {
            adapter.submitList(it)
        }
    }

    private fun setDefaultResult() {
        collectStateFlow(viewModel.searchWord) { keyword ->
            viewModel.getWantHomeResult(WantHomeResultRequest(0, 5, keyword, false)) // true 일때 오류 뱉음
        }
    }

    private fun setAvailable() {
        setDefaultResult()
        binding.cbAvailable.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                collectStateFlow(viewModel.searchWord) { keyword ->
                    viewModel.getWantHomeResult(WantHomeResultRequest(0, 5, keyword, true))
                }
            } else {
                collectStateFlow(viewModel.searchWord) { keyword ->
                    viewModel.getWantHomeResult(WantHomeResultRequest(0, 5, keyword, false))
                }
            }
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

    override fun onDestroy() {
        super.onDestroy()
        binding.rvWanthomeResult.adapter = null
    }
}
