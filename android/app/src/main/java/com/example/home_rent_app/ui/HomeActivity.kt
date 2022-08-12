package com.example.home_rent_app.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        binding.navigation.setupWithNavController(navHostFragment.navController)
    }

    fun hideBottomNavigationView(state: Boolean) {
        if (state) binding.navigation.visibility = View.GONE
        else binding.navigation.visibility = View.VISIBLE
    }
}
