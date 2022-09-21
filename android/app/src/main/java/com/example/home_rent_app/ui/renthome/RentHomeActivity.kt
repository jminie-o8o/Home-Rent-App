package com.example.home_rent_app.ui.renthome

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.ActivityRenthomeBinding
import com.example.home_rent_app.ui.renthome.viewmodel.RentHomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RentHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRenthomeBinding

    private val viewModel: RentHomeViewModel by viewModels()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_renthome)
        binding.lifecycleOwner = this

        val navHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.fragment_container_view_in_transfer) as NavHostFragment
        navController = navHostFragment.navController

        setNavigationIconClick()
    }

    private fun setNavigationIconClick() {
        binding.topAppBar.setNavigationOnClickListener {
            when (viewModel.page.value) {
                0 -> finish()
                else -> navController.navigateUp()
            }
        }
    }
}
