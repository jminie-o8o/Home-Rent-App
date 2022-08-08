package com.example.home_rent_app.ui.transfer.step2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.FragmentPicChoiceBinding
import com.example.home_rent_app.ui.transfer.TransferViewModel

class PicChoiceFragment : Fragment() {

    private val binding: FragmentPicChoiceBinding by lazy {
        FragmentPicChoiceBinding.inflate(layoutInflater)
    }

    private val viewModel: TransferViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

}