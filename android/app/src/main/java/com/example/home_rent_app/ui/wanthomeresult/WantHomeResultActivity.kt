package com.example.home_rent_app.ui.wanthomeresult

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.home_rent_app.R
import com.example.home_rent_app.data.model.WantHomeResultRequest
import com.example.home_rent_app.databinding.ActivityWantHomeResultBinding
import com.example.home_rent_app.ui.viewmodel.WantHomeResultViewModel
import com.example.home_rent_app.util.collectStateFlow
import com.example.home_rent_app.util.setLikeClickEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WantHomeResultActivity : AppCompatActivity() {

    lateinit var binding: ActivityWantHomeResultBinding
    private val viewModel: WantHomeResultViewModel by viewModels()
    lateinit var adapter: WantHomeResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_want_home_result)
        binding.lifecycleOwner = this
        handleSearchWord()
//        updateSearchWord()
        setAvailable()
        adapter = WantHomeResultAdapter(viewModel, lifecycleScope)
        binding.rvWanthomeResult.adapter = adapter
        updateAdapter()
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
        collectStateFlow(viewModel.searchWord) { keyword ->
            viewModel.getWantHomeResult(WantHomeResultRequest(0, 5, keyword, false))
        }
//        binding.cbAvailable.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                collectStateFlow(viewModel.searchWord) { keyword ->
//                    viewModel.getWantHomeResult(WantHomeResultRequest(0, 5, keyword, false))
//                }
//            } else {
//                collectStateFlow(viewModel.searchWord) { keyword ->
//                    viewModel.getWantHomeResult(WantHomeResultRequest(0, 5, keyword, false))
//                }
//            }
//        }
    }

    private fun addBookmark() {

    }
}
