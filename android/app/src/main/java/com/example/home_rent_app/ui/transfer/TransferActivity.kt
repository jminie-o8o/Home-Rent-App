package com.example.home_rent_app.ui.transfer

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.ActivityTransferBinding
import com.example.home_rent_app.ui.viewmodel.TransferViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransferBinding

    private val viewModel: TransferViewModel by viewModels()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_transfer)
        binding.lifecycleOwner = this

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view_in_transfer) as NavHostFragment
        navController = navHostFragment.navController

        setNavigationIconClick()
    }

    private fun setNavigationIconClick() {
        binding.topAppBar.setNavigationOnClickListener {
            when(viewModel.page.value) {
                0 -> finish()
                else -> navController.navigateUp()
            }
        }
    }
}
