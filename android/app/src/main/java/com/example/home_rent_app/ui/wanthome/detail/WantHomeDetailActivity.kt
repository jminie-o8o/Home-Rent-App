package com.example.home_rent_app.ui.wanthome.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.ActivityWantHomeDetailBinding
import com.example.home_rent_app.ui.viewmodel.WantHomeViewModel
import com.example.home_rent_app.ui.wanthome.WantHomeActivity
import com.example.home_rent_app.util.setLikeClickEvent

class WantHomeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWantHomeDetailBinding
    private val viewModel: WantHomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_want_home_detail)
        binding.lifecycleOwner = this
        goHomeActivity()
        clickLikeButton()
    }

    private fun goHomeActivity() {
        binding.btnGoToHome.setOnClickListener {
            val intent = Intent(this, WantHomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun clickLikeButton() {
        binding.btnLike.setLikeClickEvent(lifecycleScope) {
            binding.btnLike.isSelected = binding.btnLike.isSelected != true
        }
    }
}
