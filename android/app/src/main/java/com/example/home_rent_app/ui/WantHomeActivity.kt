package com.example.home_rent_app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.ActivityTakeHomeBinding

class WantHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTakeHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_take_home)
        binding.lifecycleOwner = this
    }
}
