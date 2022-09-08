package com.example.home_rent_app.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.home_rent_app.databinding.FragmentBookmarkBinding
import com.example.home_rent_app.ui.home.HomeActivity
import com.example.home_rent_app.util.BookMarkViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private val binding: FragmentBookmarkBinding by lazy {
        FragmentBookmarkBinding.inflate(layoutInflater)
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
        binding.vpBookmark.adapter = BookMarkViewPagerAdapter(activity as HomeActivity)
        TabLayoutMediator(binding.tlBookMark, binding.vpBookmark) { tab, position ->
            when (position) {
                0 -> tab.text = "양도해요"
                1 -> tab.text = "양수해요"
            }
        }.attach()
    }
}
