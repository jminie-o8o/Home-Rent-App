package com.example.home_rent_app.ui.profile.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.home_rent_app.ui.profile.ProfileRentHomeFragment
import com.example.home_rent_app.ui.profile.ProfileWantHomeFragment

class ProfileViewPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {
    companion object {
        const val NUM_PAGE = 2
    }

    override fun getItemCount(): Int = NUM_PAGE

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProfileRentHomeFragment()
            else -> ProfileWantHomeFragment()
        }
    }
}
