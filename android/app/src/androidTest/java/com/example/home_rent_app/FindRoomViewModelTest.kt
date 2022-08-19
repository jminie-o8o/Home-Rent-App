package com.example.home_rent_app

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.home_rent_app.databinding.ActivityFindRoomBinding
import com.example.home_rent_app.ui.findroom.FindRoomActivity
import com.example.home_rent_app.ui.viewmodel.FindRoomViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class FindRoomViewModelTest {

    private lateinit var dispatchers : CoroutineDispatcher

    @get:Rule
    val activityScenarioRule: ActivityScenarioRule<FindRoomActivity> =
        ActivityScenarioRule(
            Intent(
                ApplicationProvider.getApplicationContext(),
                FindRoomActivity::class.java
            )
        )

    @Before
    fun setUp() {
        dispatchers = Dispatchers.Unconfined
    }

    @Test
    fun editTextTest() {
//        Assert.assertThat(viewModel.searchAddress.value, CoreMatchers.`is`("사상구"))
    }
}