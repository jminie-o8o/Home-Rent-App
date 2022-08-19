package com.example.home_rent_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.home_rent_app.util.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class WantHomeViewModel @Inject constructor() : ViewModel() {

    val location = MutableStateFlow(Location.SEOUL)
}
