package com.example.home_rent_app.ui.findroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.home_rent_app.databinding.ActivityFindRoomBinding

class FindRoomActivity : AppCompatActivity() {

    private val binding: ActivityFindRoomBinding by lazy {
        ActivityFindRoomBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar

    }
}