package com.example.home_rent_app

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.home_rent_app.ui.home.HomeFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeTest {

    @Test
    fun moveToFindRoomActivityTest() {
        launchFragmentInContainer<HomeFragment>().apply {
            onView(withId(R.id.btn_rent)).perform(click())
        }
        onView(withId(R.id.clo_find_room_activity)).check(matches(isDisplayed()))
    }

    @Test
    fun onViewBtn() {
        launchFragmentInContainer<HomeFragment>().apply {
            onView(withId(R.id.btn_rent)).check(matches(isDisplayed()))
            onView(withId(R.id.temp_btn)).check(matches(isDisplayed()))
            onView(withId(R.id.temp_button)).check(matches(isDisplayed()))
            onView(withId(R.id.btn_wanted)).check(matches(isDisplayed()))
        }
    }

}