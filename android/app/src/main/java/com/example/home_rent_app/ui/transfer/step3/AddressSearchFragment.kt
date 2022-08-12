package com.example.home_rent_app.ui.transfer.step3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.home_rent_app.databinding.FragmentAddressSearchBinding

class AddressSearchFragment : Fragment() {

    private val binding: FragmentAddressSearchBinding by lazy {
        FragmentAddressSearchBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}
