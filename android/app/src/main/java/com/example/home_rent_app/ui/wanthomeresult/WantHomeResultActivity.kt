package com.example.home_rent_app.ui.wanthomeresult

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.ActivityWantHomeResultBinding

class WantHomeResultActivity : AppCompatActivity() {

    lateinit var binding: ActivityWantHomeResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_want_home_result)
        binding.lifecycleOwner = this
    }
}
