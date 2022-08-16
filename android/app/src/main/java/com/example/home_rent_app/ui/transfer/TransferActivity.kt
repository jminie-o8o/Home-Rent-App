package com.example.home_rent_app.ui.transfer

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.ActivityTransferBinding

class TransferActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransferBinding

    private val viewModel: TransferViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_transfer)
        binding.lifecycleOwner = this

        setNavigationIconClick()
    }

    private fun setNavigationIconClick() {
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
    }
}
