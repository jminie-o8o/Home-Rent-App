package com.example.home_rent_app.ui.transfer.step1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.home_rent_app.databinding.FragmentHomeDescriptionBinding

class HomeDescriptionFragment : Fragment() {

    private val binding: FragmentHomeDescriptionBinding by lazy {
        FragmentHomeDescriptionBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}