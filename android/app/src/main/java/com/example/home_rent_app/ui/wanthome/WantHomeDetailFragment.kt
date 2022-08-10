package com.example.home_rent_app.ui.wanthome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.FragmentWantHomeDetailBinding

class WantHomeDetailFragment : Fragment() {

    lateinit var binding: FragmentWantHomeDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_want_home_detail, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pressLikeButton()
    }

    private fun pressLikeButton() {
        binding.btnLike.setOnClickListener {
            binding.btnLike.isSelected = binding.btnLike.isSelected != true
        }
    }
}
