package com.example.home_rent_app.ui.wanthomeresult

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.home_rent_app.R
import com.example.home_rent_app.data.model.WantHomeResultRequest
import com.example.home_rent_app.databinding.ActivityWantHomeResultBinding
import com.example.home_rent_app.ui.viewmodel.WantHomeResultViewModel
import com.example.home_rent_app.util.collectStateFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WantHomeResultActivity : AppCompatActivity() {

    lateinit var binding: ActivityWantHomeResultBinding
    private val viewModel: WantHomeResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_want_home_result)
        binding.lifecycleOwner = this
        handleSearchWord()
        updateSearchWord()
    }

    private fun handleSearchWord() {
        binding.etWantHome.addTextChangedListener { text ->
            if (text != null) viewModel.handleSearchWork(text.toString())
        }
    }

    private fun updateSearchWord() {
        collectStateFlow(viewModel.searchWord) { keyword ->
            viewModel.getWantHomeResult(WantHomeResultRequest(0, 5, keyword, false))
        }
    }
}
