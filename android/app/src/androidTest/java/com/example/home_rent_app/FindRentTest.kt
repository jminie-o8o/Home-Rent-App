package com.example.home_rent_app

import android.content.Intent
import android.graphics.ColorSpace.match
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.home_rent_app.ui.findroom.FindRoomActivity
import com.example.home_rent_app.ui.viewmodel.FindRoomViewModel
import com.google.android.material.textfield.TextInputEditText
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class FindRentTest {

    @get:Rule
    val activityScenarioRule: ActivityScenarioRule<FindRoomActivity> =
        ActivityScenarioRule(
            Intent(
                ApplicationProvider.getApplicationContext(),
                FindRoomActivity::class.java
            )
        )

    @Test
    fun setAppbarBackButtonTest() {
        activityScenarioRule.scenario.onActivity {
            it.onBackPressed()
            it.finish()
        }
    }

    @Test
    fun onViewEditTextTest() {
        activityScenarioRule.scenario.apply {
            onView(withId(R.id.tilo_search_room)).check(matches(isDisplayed()))
        }
    }

}