package com.example.home_rent_app.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.FragmentProfileGiveHomeBinding
import com.example.home_rent_app.util.ItemIdSession
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileGiveHomeFragment : Fragment() {

    lateinit var binding: FragmentProfileGiveHomeBinding
    lateinit var adapter: ProfileGiveHomeAdapter
    @Inject
    lateinit var idSession: ItemIdSession

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_give_home, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ProfileGiveHomeAdapter(idSession)
        binding.rvProfileGiveHome.adapter = adapter
    }
}
