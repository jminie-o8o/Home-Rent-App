package com.example.home_rent_app

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.home_rent_app.ui.findroom.FindHomeActivity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class FindRoomViewModelTest {

    private lateinit var dispatchers: CoroutineDispatcher

    @get:Rule
    val activityScenarioRule: ActivityScenarioRule<FindHomeActivity> =
        ActivityScenarioRule(
            Intent(
                ApplicationProvider.getApplicationContext(),
                FindHomeActivity::class.java
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
