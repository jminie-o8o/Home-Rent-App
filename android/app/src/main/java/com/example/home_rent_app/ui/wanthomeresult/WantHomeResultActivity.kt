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
import com.example.home_rent_app.ui.viewmodel.WantHomeViewModel
import com.example.home_rent_app.util.UserSession
import com.example.home_rent_app.util.collectLatestStateFlow
import com.example.home_rent_app.util.collectStateFlow
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WantHomeResultActivity : AppCompatActivity() {

    lateinit var binding: ActivityWantHomeResultBinding
    private val viewModel: WantHomeViewModel by viewModels()
    lateinit var adapter: WantHomeResultAdapter
    @Inject
    lateinit var userSession: UserSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_want_home_result)
        binding.lifecycleOwner = this
        handleSearchWord()
        setDefaultResult()
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
        collectLatestStateFlow(viewModel.wantHomeResult) {
            adapter.submitData(it)
        }
    }

    private fun setDefaultResult() {
        collectStateFlow(viewModel.searchWord) { keyword ->
            viewModel.getWantHomeResult(
                WantHomeResultRequest(
                    keyword,
                    binding.cbAvailable.isChecked
                )
            )
        }
    }

    private fun setAvailable() {
        binding.cbAvailable.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.getWantHomeResult(
                    WantHomeResultRequest(
                        binding.etWantHome.text.toString(),
                        true
                    )
                )
            } else {
                viewModel.getWantHomeResult(
                    WantHomeResultRequest(
                        binding.etWantHome.text.toString(),
                        false
                    )
                )
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
}
