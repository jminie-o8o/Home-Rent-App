package com.example.home_rent_app.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.FragmentProfileBinding
import com.example.home_rent_app.ui.HomeActivity
import com.example.home_rent_app.util.BookMarkViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vpProfileTab.adapter = ProfileViewPagerAdapter(activity as HomeActivity)
        TabLayoutMediator(binding.tlProfileTab, binding.vpProfileTab) { tab, position ->
            when(position) {
                0 -> tab.text = "양도해요"
                1 -> tab.text = "양수해요"
            }
        }.attach()
    }
}
