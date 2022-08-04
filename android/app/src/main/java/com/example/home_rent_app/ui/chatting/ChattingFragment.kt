package com.example.home_rent_app.ui.chatting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.home_rent_app.databinding.FragmentChattingBinding

class ChattingFragment : Fragment() {

    private val binding: FragmentChattingBinding by lazy {
        FragmentChattingBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}
