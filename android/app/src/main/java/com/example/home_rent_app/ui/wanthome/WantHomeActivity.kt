package com.example.home_rent_app.ui.wanthome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.ActivityWantHomeBinding
import com.example.home_rent_app.ui.home.HomeActivity
import com.example.home_rent_app.ui.wanthome.detail.WantHomeDetailActivity
import com.example.home_rent_app.ui.wanthome.viewmodel.WantHomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WantHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWantHomeBinding
    private val viewModel: WantHomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_want_home)
        binding.lifecycleOwner = this
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun goHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    // EditText 이외에 다른 곳을 터치하면 소프트 키보드 내려가는 기능
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }

    fun clickRegister(id: Int) {
        val intent = Intent(this, WantHomeDetailActivity::class.java)
        intent.putExtra("homeId", id)
        startActivity(intent)
        finish()
    }
}
