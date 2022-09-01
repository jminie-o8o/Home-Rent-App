package com.example.home_rent_app.ui.transfer.step4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.home_rent_app.databinding.FragmentRentHomeDescriptionBinding
import com.example.home_rent_app.ui.viewmodel.TransferViewModel

class RentHomeDescriptionFragment : Fragment() {

    private val binding by lazy {
        FragmentRentHomeDescriptionBinding.inflate(layoutInflater)
    }

    private val viewModel: TransferViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}