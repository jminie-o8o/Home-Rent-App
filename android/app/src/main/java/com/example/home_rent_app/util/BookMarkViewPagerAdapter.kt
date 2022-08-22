package com.example.home_rent_app.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.home_rent_app.ui.bookmark.BookmarkGiveHomeFragment
import com.example.home_rent_app.ui.bookmark.BookmarkWantHomeFragment

class BookMarkViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    companion object {
        const val NUM_PAGE = 2
    }

    override fun getItemCount(): Int = NUM_PAGE

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BookmarkGiveHomeFragment()
            else -> BookmarkWantHomeFragment()
        }
    }
}
